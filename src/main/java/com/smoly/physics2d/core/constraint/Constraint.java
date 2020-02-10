package com.smoly.physics2d.core.constraint;

import com.smoly.physics2d.core.geometry.Body;
import org.apache.commons.math3.linear.RealMatrix;

public abstract class Constraint {
    protected Body bodyA;
    protected Body bodyB;
    protected double rightPart = 0;

    public double getRightPart() {
        return rightPart;
    }

    public abstract RealMatrix getJ() ;
    public abstract double getBias();
    protected ConstraintType constraintType;

    public Constraint(Body bodyA, Body bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }

    protected void setConstraintType(ConstraintType constraintType) {
        this.constraintType = constraintType;
    }

    public ConstraintType getConstraintType() {
        return constraintType;
    }

    public Body getBodyA(){
        return bodyA;
    }

    public Body getBodyB(){
        return bodyB;
    }
}
