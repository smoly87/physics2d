package com.smoly.physics2d.collisions;

import com.smoly.physics2d.core.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

public class PenetrationInfo {
    protected Vector2D pA;
    protected Vector2D pB;
    protected Body bodyA;
    protected Body bodyB;

    public Vector2D getpA() {
        return pA;
    }

    public Vector2D getpB() {
        return pB;
    }

    public Body getBodyA() {
        return bodyA;
    }

    public Body getBodyB() {
        return bodyB;
    }

    public PenetrationInfo(Body bodyA, Body bodyB, Vector2D pA, Vector2D pB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
        this.pA = pA;
        this.pB = pB;
    }
}
