package com.smoly.physics2d.core.geometry.bodies.types;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.utils.MatrixUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class VectorVis extends Body {
    public VectorVis(Vector2D p1, Vector2D p2) {
        super();
        List<Vector2D> vertexes = new ArrayList<>();
        Vector2D center = (p2.subtract(p1)).scalarMultiply(0.5);
        vertexes.add(p1.subtract(center));
        vertexes.add(p2.subtract(center));
        this.setPosition(MatrixUtils.createFrom2D(center));
        this.setVertexes(vertexes);
    }
}
