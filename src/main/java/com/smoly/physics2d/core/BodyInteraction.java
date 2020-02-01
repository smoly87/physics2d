package com.smoly.physics2d.core;

import org.apache.commons.math3.linear.RealMatrix;

public abstract class BodyInteraction {
    private Body bodyA;
    private Body bodyB;
    public abstract RealMatrix getCurrentJ() ;
    public abstract double getBias();
    public BodyInteraction(Body bodyA, Body bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }
    Body getBodyA(){
        return bodyA;
    }

    Body getBodyB(){
        return bodyB;
    }
}
