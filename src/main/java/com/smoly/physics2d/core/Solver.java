package com.smoly.physics2d.core;

import com.smoly.physics2d.collisions.CollisionProcessor;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Solver {
    List<Body> bodiesList;
    List<BodyInteraction> bodyInteractionsList;
    protected CollisionProcessor collisionProcessor;

    final double beta = 0.2;
    private final int correctionStepsCount = 4;

    public Solver(List<Body> bodiesList, List<BodyInteraction> bodyInteractionsList) {
        this.bodiesList = bodiesList;
        this.bodyInteractionsList = bodyInteractionsList;
        List<BodyInteraction> bodyInteractionsGeneratedList = new ArrayList<>();
        collisionProcessor = new CollisionProcessor(bodiesList);
    }

    public void step(double dt) {
        dt = 0.001;
        List<BodyInteraction> currentInteractions =  generateDynamicInteractions();
        currentInteractions.addAll(bodyInteractionsList);

        integrateForces(dt);
        addImpulseFromInteractionsConstraints(currentInteractions, dt);
        updatePositions(dt);
    }

    private void updatePositions(double dt) {
        for (Body body : bodiesList) {
            body.setPosition(body.getPosition().add(body.getV().scalarMultiply(dt)));
        }
    }
    private void integrateForces(double dt) {
        for (Body body : bodiesList) {
            integrateBodyForces(body, dt);
        }
    }
    private void integrateBodyForces(Body body, double dt) {
        for(Force force: body.getForces()) {
            // Torque connected to the force. Is Z component of r x F
            Vector2D r = body.getAbsCoord(force.applyPoint).subtract(body.getCenter());
            double tau = MatrixUtils.crossProduct2d(r, force.getForce());
            RealMatrix F = new Array2DRowRealMatrix(new double[] {force.getForce().getX(), force.getForce().getY(), tau  });
            RealMatrix MInv = body.getInverseM();
            double[] deltaVelocity = MInv.multiply(F).scalarMultiply(dt).getColumn(0); // delta velocity is matrix 3*1 contains vX, vY, w
            body.setV(body.getV().add(new Vector3D(deltaVelocity)));
        }
    }


    private void addImpulseFromInteractionsConstraints(List<BodyInteraction> bodyInteractionsList, double dt) {
        Map<BodyInteraction, RealMatrix> JMap = new HashMap<>();
        Map<BodyInteraction, Double> biasMap = new HashMap<>();
        Map<BodyInteraction, RealMatrix> MInvABMap = new HashMap<>();

        for (BodyInteraction bodyInteraction : bodyInteractionsList) {
            Body A = bodyInteraction.getBodyA();
            Body B = bodyInteraction.getBodyB();
            double mA = A.getM();
            double mB = B.getM();
            double iA = A.getI();
            double iB = B.getI();
            RealMatrix J = bodyInteraction.getJ();
            double bias = beta /  dt * bodyInteraction.getBias();
            RealMatrix MInvAB = new Array2DRowRealMatrix(MatrixUtils.diagMatrix(new double[]{mB, mB, iB, mA, mA, iA })); // United velocity vector of both bodies.

            MInvABMap.put(bodyInteraction, MInvAB);
            JMap.put(bodyInteraction, J);
            biasMap.put(bodyInteraction, bias);
        }
        for (int i = 0; i < correctionStepsCount; i++) {
            for (BodyInteraction bodyInteraction : bodyInteractionsList) {
                Body A = bodyInteraction.getBodyA();
                Body B = bodyInteraction.getBodyB();
                RealMatrix J = JMap.get(bodyInteraction);
                double bias = biasMap.get(bodyInteraction);
                RealMatrix MInvAB = MInvABMap.get(bodyInteraction);

                RealMatrix vAB = new Array2DRowRealMatrix(MatrixUtils.concat(B.getV().toArray(), A.getV().toArray())); // United velocity vector of both bodies.

                double labmdaNumerator = J.multiply(vAB).getEntry(0, 0) + bias;
                double labmdaDenom = J.multiply(MInvAB.multiply(J.transpose())).getEntry(0, 0);
                if (Math.abs(labmdaDenom) < 1e-15) {
                    continue;
                }
                double lambda = -labmdaNumerator / labmdaDenom;
                vAB = vAB.add(MInvAB.multiply(J.transpose()).scalarMultiply(lambda));
                double[] vABValues = vAB.getColumn(0);
                B.setV(new Vector3D(MatrixUtils.getPartial(vABValues, 0, 3)));
                A.setV(new Vector3D(MatrixUtils.getPartial(vABValues, 3, 3)));
                // Clamp !!!
            }
        }

    }

    private List<BodyInteraction> generateDynamicInteractions() {
        return collisionProcessor.generateDynamicInteraction();
    }
}
