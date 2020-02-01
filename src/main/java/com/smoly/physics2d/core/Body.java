package com.smoly.physics2d.core;

import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.List;


public class Body {
    public Vector3D getPosition() {
        return position;
    }

    public Body() {
        forces = new ArrayList<>();
        v = new Vector3D(0d,0d,0d);
    }

    public void setPosition(Vector3D position) {
        this.position = position;
    }

    protected Vector3D position; // coords of center and theta
    protected List<Vector2D> vertexes;
    protected double I = 1; // moment of inertia
    protected double m = 1; // mass
    protected Vector3D v; // velocity x,y,w (linear and angular)
    protected List<Force> forces;

    public RealMatrix getInverseM() {
        return new Array2DRowRealMatrix(new double[][] {
                {m,0,0},
                {0,m,0},
                {0,0,I}
        });
    }

    public Vector2D getAbsCoord(Vector2D relCoord) {
        return  getCenter().add(new Vector2D(getTransformationMatrix().multiply(new Array2DRowRealMatrix(relCoord.toArray())).getColumn(0)));
    }

    public Vector2D getCenter() {
        return new Vector2D(position.getX(), position.getY());
    }

    public RealMatrix getTransformationMatrix() {
        double theta = position.getZ() * Math.PI;
        return new Array2DRowRealMatrix(new double[][]{
                {cos(theta), -sin(theta)},
                {sin(theta), cos(theta)},
        });
    }

    public List<Vector2D> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vector2D> vertexes) {
        this.vertexes = vertexes;
    }

    public double getI() {
        return I;
    }

    public void setI(double i) {
        I = i;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public Vector3D getV() {
        return v;
    }

    public void setV(Vector3D v) {
        this.v = v;
    }

    public List<Force> getForces() {
        return forces;
    }

    public void setForces(List<Force> forces) {
        this.forces = forces;
    }
}
