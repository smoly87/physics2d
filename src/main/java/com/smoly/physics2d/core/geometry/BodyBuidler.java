package com.smoly.physics2d.core.geometry;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class BodyBuidler {
    protected List<Vector2D> vertexes;
    List<Force> bodyForces = new ArrayList<>();
    protected double mInv = 1;
    protected double invI = 1;

    public BodyBuidler setInvI(double invI) {
        this.invI = invI;
        return this;
    }

    public BodyBuidler setPosition(Vector3D position) {
        this.position = position;
        return this;
    }

    protected Vector3D position; // coords of center and theta

    private BodyBuidler() {
        vertexes = new ArrayList<>();
        bodyForces = new ArrayList<>();
        position = new Vector3D(0d,0d,0d);
    }

    public static BodyBuidler newBuilder() {
        return new BodyBuidler();
    }

    public Body build() {
        Body b = new Body();
        b.setVertexes(vertexes);
        b.setPosition(position);
        b.setForces(bodyForces);
        b.setInvM(mInv);
        b.setInvI(invI);
        return b;
    }

    public BodyBuidler addAllVertex(List<Vector2D> vertexesList) {
        vertexes.addAll(vertexesList);
        return this;
    }

    public BodyBuidler addVertex(double x, double y) {
        vertexes.add(new Vector2D(x,y));
        return this;
    }

    public BodyBuidler setInvMass(double mInv) {
        this.mInv = mInv;
        return this;
    }

    public BodyBuidler addForce(Vector2D position,Vector2D force) {
        bodyForces.add(new Force(position, force));
        return this;
    }
}
