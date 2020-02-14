package com.smoly.physics2d.core.constraint;

import com.smoly.physics2d.core.geometry.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import static com.smoly.physics2d.core.utils.MatrixUtils.crossProduct2d;

public class FrictionConstraint extends Constraint {
    private final Vector2D pA;
    private final Vector2D t;
    public Vector2D getpA() {
        return pA;
    }

    public Vector2D getpB() {
        return pB;
    }

    private final Vector2D pB;
    public FrictionConstraint(Body bodyA, Body bodyB, Vector2D pA, Vector2D pB, Vector2D n) {
        super(bodyA, bodyB);
        this.setConstraintType(ConstraintType.EQUALITY);
        this.pA = pA;
        this.pB = pB;
        this.t = new Vector2D(-n.getY(), n.getX());
    }

    @Override
    public RealMatrix getJ() {
        Vector2D cA = bodyA.getCenter();
        Vector2D cB = bodyB.getCenter();

        double[] J = new double[]{
                t.getX(),
                t.getY(),
                crossProduct2d(pA.subtract(cA), t),
                -t.getX(),
                -t.getY(),
                -crossProduct2d(pB.subtract(cB), t),
        };
        return new Array2DRowRealMatrix(J).transpose();
    }

    @Override
    public double getBias() {

        return ( 0);
    }
}
