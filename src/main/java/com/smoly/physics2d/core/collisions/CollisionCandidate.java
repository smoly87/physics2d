package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.Body;

public class CollisionCandidate {
    protected Body bodyA;
    protected Body bodyB;

    public CollisionCandidate(Body bodyA, Body bodyB) {
        this.bodyA = bodyA;
        this.bodyB = bodyB;
    }


}
