package com.smoly.physics2d.scenes.chain;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.MatrixUtils;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class VectorVis extends Body {
    public VectorVis(Vector2D fromPoint, Vector2D vec) {
        super();
        List<Vector2D> vertexes = new ArrayList<>();
        vertexes.add(new Vector2D(0d,0d));
        vertexes.add(vec.subtract(fromPoint));
        this.setPosition(MatrixUtils.createFrom2D(fromPoint));
        this.setVertexes(vertexes);
    }
}
