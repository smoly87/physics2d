package com.smoly.physics2d.collisions;

import com.smoly.physics2d.core.Body;

public class CollisionCandidate {
    protected Body bodyA;

    public CollisionCandidate(Body bodyA, Body bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }

    protected Body bodyB;

}
