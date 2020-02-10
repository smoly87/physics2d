package com.smoly.physics2d.core.geometry.bodies.types;

import com.smoly.physics2d.core.geometry.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class QuadBody extends Body {
    public QuadBody() {
        super();
        List<Vector2D> vertexesList = new ArrayList<>();
        vertexesList.add(new Vector2D(-0.5d, -0.5d));
        vertexesList.add(new Vector2D(0.5d, -0.5d));
        vertexesList.add(new Vector2D(0.5d, 0.5d));
        vertexesList.add(new Vector2D(-0.5d, 0.5d));
        this.setVertexes(vertexesList);
    }
}
