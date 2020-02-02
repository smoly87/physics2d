package com.smoly.physics2d.collisions;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.BodyInteraction;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.RealMatrix;

public class Collision extends BodyInteraction {
    Vector2D pA;
    Vector2D pB;
    public Collision(Body bodyA, Body bodyB, Vector2D pA, Vector2D pB) {
        super(bodyA, bodyB);
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
