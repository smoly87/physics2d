package com.smoly.physics2d.core.geometry.bodies.types;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class SimpleGeometryFactory {
    public static List<Vector2D> getQuadVertexes() {
        List<Vector2D> vertexesList = new ArrayList<>();
        vertexesList.add(new Vector2D(-0.5d, -0.5d));
        vertexesList.add(new Vector2D(0.5d, -0.5d));
        vertexesList.add(new Vector2D(0.5d, 0.5d));
        vertexesList.add(new Vector2D(-0.5d, 0.5d));
        return vertexesList;
    }

    public static List<Vector2D> getRectangleVertexes(double w, double h) {
        List<Vector2D> vertexesList = new ArrayList<>();
        vertexesList.add(new Vector2D(-w / 2, -h / 2));
        vertexesList.add(new Vector2D(w / 2, -h / 2));
        vertexesList.add(new Vector2D(w / 2, h / 2));
        vertexesList.add(new Vector2D(-w / 2, h / 2));
        return vertexesList;
    }
}
