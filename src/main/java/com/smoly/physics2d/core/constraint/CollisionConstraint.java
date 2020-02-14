package com.smoly.physics2d.core.constraint;

import com.smoly.physics2d.core.geometry.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import static com.smoly.physics2d.core.utils.MatrixUtils.crossProduct2d;

public class CollisionConstraint extends Constraint {
    private final Vector2D pA;
    private final Vector2D n;
    public Vector2D getpA() {
        return pA;
    }

    public Vector2D getpB() {
        return pB;
    }

    private final Vector2D pB;
    public CollisionConstraint(Body bodyA, Body bodyB, Vector2D pA, Vector2D pB, Vector2D n) {
        super(bodyA, bodyB);
        this.setConstraintType(ConstraintType.INEQUALITY);
        this.pA = pA;
        this.pB = pB;
        this.n = n;
    }

    @Override
    public RealMatrix getJ() {
        Vector2D cA = bodyA.getCenter();
        Vector2D cB = bodyB.getCenter();

        double[] J = new double[]{
                n.getX(),
                n.getY(),
                crossProduct2d(pA.subtract(cA),  n),
                -n.getX(),
                -n.getY(),
                -crossProduct2d(pB.subtract(cB),  n),
        };
        return new Array2DRowRealMatrix(J).transpose();
    }

    @Override
    public double getBias() {
        double C = pA.subtract(pB).dotProduct(n);
        return ((C < 0) ? C : 0);
    }
}
