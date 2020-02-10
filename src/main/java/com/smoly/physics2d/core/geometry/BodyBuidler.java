package com.smoly.physics2d.core.geometry;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class BodyBuidler {
    protected List<Vector2D> vertexes;

    private BodyBuidler() {
        vertexes = new ArrayList<>();
    }

    public static BodyBuidler newBuilder() {
        return new BodyBuidler();
    }

    public Body build() {
        Body b = new Body();
        b.setVertexes(vertexes);
        return b;
    }

    public BodyBuidler addVertex(double x, double y) {
        vertexes.add(new Vector2D(x,y));
        return this;
    }

    public BodyBuidler setInvMass() {
        return this;
    }
}
