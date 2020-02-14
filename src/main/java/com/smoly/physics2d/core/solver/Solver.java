package com.smoly.physics2d.core.solver;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.Force;
import com.smoly.physics2d.core.utils.MatrixUtils;
import com.smoly.physics2d.core.constraint.Constraint;
import com.smoly.physics2d.core.constraint.ConstraintType;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.util.*;
import java.util.stream.Collectors;

public class Solver {
    protected final Set<DynamicConstraintsProcessor> processors;
    protected final SolverConfig solverConfig;

    @Inject
    public Solver(Set<DynamicConstraintsProcessor> processors, SolverConfig solverConfig) {
        this.processors = processors;
        this.solverConfig = solverConfig;
    }

    public void step(List<Body> bodiesList, List<Constraint> constraintList, double dt) {
        List<Constraint> currentInteractions =  generateDynamicInteractions(bodiesList);
        currentInteractions.addAll(constraintList);

        integrateForces(bodiesList, dt);
        addImpulseFromInteractionsConstraints(currentInteractions, dt);
        updatePositions(bodiesList, dt);
    }

    private void updatePositions(List<Body> bodiesList, double dt) {
        for (Body body : bodiesList) {
            body.setPosition(body.getPosition().add(body.getV().scalarMultiply(dt)));
        }
    }

    private void integrateForces(List<Body> bodiesList, double dt) {
        for (Body body : bodiesList) {
            integrateBodyForces(body, dt);
        }
    }

    private void integrateBodyForces(Body body, double dt) {
        for(Force force: body.getForces()) {
            // Torque connected to the force. Is Z component of r x F
            Vector2D r = body.getAbsCoord(force.getApplyPoint()).subtract(body.getCenter());
            double tau = MatrixUtils.crossProduct2d(r, force.getForce());
            RealMatrix F = new Array2DRowRealMatrix(new double[] {force.getForce().getX(), force.getForce().getY(), tau  });
            RealMatrix MInv = body.getInverseM();
            double[] deltaVelocity = MInv.multiply(F).scalarMultiply(dt).getColumn(0); // delta velocity is matrix 3*1 contains vX, vY, w
            body.setV(body.getV().add(new Vector3D(deltaVelocity)));
        }
    }

    private void addImpulseFromInteractionsConstraints(List<Constraint> bodyInteractionsList, double dt) {
        Map<Constraint, RealMatrix> JMap = new HashMap<>();
        Map<Constraint, Double> biasMap = new HashMap<>();
        Map<Constraint, RealMatrix> MInvABMap = new HashMap<>();
        Map<Constraint, Double> accumulatedLambdaMap = new HashMap<>();

        for (Constraint constraint : bodyInteractionsList) {
            Body A = constraint.getBodyA();
            Body B = constraint.getBodyB();
            double mA = A.getInvM();
            double mB = B.getInvM();
            double iA = A.getInvI();
            double iB = B.getInvI();
            RealMatrix J = constraint.getJ();
            double bias = solverConfig.beta() /  dt * constraint.getBias();
            RealMatrix MInvAB = new Array2DRowRealMatrix(MatrixUtils.diagMatrix(new double[]{ mA, mA, iA, mB, mB, iB })); // United velocity vector of both bodies.

            MInvABMap.put(constraint, MInvAB);
            JMap.put(constraint, J);
            biasMap.put(constraint, bias);
            accumulatedLambdaMap.put(constraint, 0d);
        }
        double maxLambda = -11111;
        for (int i = 0; i < solverConfig.correctionStepsCount(); i++) {
            for (Constraint constraint : bodyInteractionsList) {
                double lambdaAccumulated = accumulatedLambdaMap.get(constraint);
                Body A = constraint.getBodyA();
                Body B = constraint.getBodyB();
                RealMatrix J = JMap.get(constraint);
                double bias = solverConfig.beta() /  dt * constraint.getBias();
                RealMatrix MInvAB = MInvABMap.get(constraint);

                RealMatrix vAB = new Array2DRowRealMatrix(MatrixUtils.concat(A.getV().toArray(), B.getV().toArray())); // United velocity vector of both bodies.

                double labmdaNumerator = J.multiply(vAB).getEntry(0, 0) + bias;
                double labmdaDenom = J.multiply(MInvAB.multiply(J.transpose())).getEntry(0, 0);
                if (Math.abs(labmdaDenom) < 1e-15) {
                    continue;
                }
                double lambda = -labmdaNumerator / labmdaDenom;
                if (constraint.getConstraintType().equals(ConstraintType.INEQUALITY)) {
                    if (lambdaAccumulated + lambda < 0) {
                        lambda = -lambdaAccumulated;
                    }
                    lambdaAccumulated += lambda;
                    accumulatedLambdaMap.put(constraint, lambdaAccumulated);
                }
                if(lambda > maxLambda) maxLambda = lambda;
                //if (lambda > 0.1) lambda = 0.1;
                vAB = vAB.add(MInvAB.multiply(J.transpose()).scalarMultiply(lambda));
                double[] vABValues = vAB.getColumn(0);

                A.setV(new Vector3D(MatrixUtils.getPartial(vABValues, 0, 3)));
                B.setV(new Vector3D(MatrixUtils.getPartial(vABValues, 3, 3)));
            }
        }
        //if (maxLambda > 1e-2)System.out.println(maxLambda);

    }

    protected List<Constraint> generateDynamicInteractions(List<Body> bodiesList) {
        return processors.stream().map(processor -> processor
                .generateDynamicConstraints(bodiesList))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
