package com.smoly.physics2d.core.collisions;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.Edge;
import com.smoly.physics2d.core.utils.CanvasPointWithLabel;
import com.smoly.physics2d.core.utils.LineIntersectionUtil;
import com.smoly.physics2d.core.utils.SceneDebuger;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PenetrationTester {
    protected Body bodyA;
    protected Body bodyB;
    protected final SceneDebuger sceneDebuger;

    @Inject
    public PenetrationTester(SceneDebuger sceneDebuger) {
        this.sceneDebuger = sceneDebuger;
    }

    public List<PenetrationInfo> getPenetrationsList(Body b1, Body b2) {
        List<PenetrationInfo> vertsInside = b1.getVertexesAbs().stream()
                .filter(v -> isVertexInsideBody(v, b2))
                .map(v -> new PenetrationInfo(b1 ,b2, v, getClosestEdgePB(v, b1, b2)))
                .filter(penInfo -> penInfo.pB != null)
                .collect(Collectors.toList());
        return vertsInside;
    }

    public Vector2D getClosestEdgePB(Vector2D v,Body bodyA, Body bodyB) {
        sceneDebuger.addPoint(new CanvasPointWithLabel(v, Color.ORANGE) );
        sceneDebuger.addPoint(new CanvasPointWithLabel(bodyA.getCenter(), Color.ORANGE) );

        for( Edge e : bodyB.getEdgesAbs()) {
            Optional<Vector2D> intersectRes = LineIntersectionUtil
                    .getLinesIntersections(e.getV1(), e.getV2(), bodyA.getCenter(), v);
            if (!intersectRes.isPresent()) continue;
            Vector2D p = intersectRes.get();
            sceneDebuger.addPoint(new CanvasPointWithLabel(p, Color.red) );
            sceneDebuger.addPoint(new CanvasPointWithLabel(e.getV1(), Color.green) );
            sceneDebuger.addPoint(new CanvasPointWithLabel(e.getV2(), Color.green) );

            if (LineIntersectionUtil.vertexInBound(p, v, bodyA.getCenter())) {
                Vector2D minusN = e.getN().scalarMultiply(-1);
                double nProj = (v.subtract(p)).dotProduct(minusN);
                return v.subtract(minusN.scalarMultiply(nProj)) ;
            }
        }
        //throw new IllegalStateException("Could not find the closest point in the intersection");
        return null;
    }

    protected boolean isVertexInsideBody(Vector2D v,  Body b) {
        for(Edge e : b.getEdgesAbs()) {
            Vector2D p = v.subtract(e.getMiddle());
            // If projection to normal for at least of one edge is positive hence the vertex is outside the body
            if (p.dotProduct(e.getN()) > 0) {
                return false;
            }

        }
        return true;
    }
}
