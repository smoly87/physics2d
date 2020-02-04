package com.smoly.physics2d.core.constraint;

import com.smoly.physics2d.core.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.RealMatrix;

public class CollisionConstraint extends Constraint {
    Vector2D pA;
    Vector2D pB;
    public CollisionConstraint(Body bodyA, Body bodyB, Vector2D pA, Vector2D pB) {
        super(bodyA, bodyB);
        this.setConstraintType(ConstraintType.INEQUALITY);
    }

    @Override
    public RealMatrix getJ() {
        return null;
    }

    @Override
    public double getBias() {
        return 0;
    }
}
