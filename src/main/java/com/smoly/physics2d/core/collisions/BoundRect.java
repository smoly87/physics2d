package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.geometry.Body;

public class BoundRect {
    protected double x;
    protected double y;
    protected double width;
    protected double height;
    protected Body body;

    public Body getBody() {
        return body;
    }

    public BoundRect(Body body, double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.body = body;
    }

    public boolean isIntersectsWith(BoundRect rect2) {
        return (this.x < rect2.x + rect2.width &&
                this.x + this.width > rect2.x &&
                this.y < rect2.y + rect2.height &&
                this.y + this.height > rect2.y) ;
    }
}
