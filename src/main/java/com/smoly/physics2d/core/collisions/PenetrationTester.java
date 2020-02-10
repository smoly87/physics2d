package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.Edge;
import com.smoly.physics2d.core.utils.LineIntersectionUtil;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PenetrationTester {
    protected Body bodyA;
    protected Body bodyB;

    public List<PenetrationInfo> getPenetrationsList(Body b1, Body b2) {
        List<PenetrationInfo> vertsInside = b1.getVertexesAbs().stream()
                .filter(v -> isVertexInsideBody(v, b2))
                .map(v -> new PenetrationInfo(b1 ,b2, v, getClosestEdgePB(v, b1, b2)))
                .collect(Collectors.toList());
        return vertsInside;
    }

    public Vector2D getClosestEdgePB(Vector2D v,Body bodyA, Body bodyB) {
        for( Edge e : bodyB.getEdgesAbs()) {
            Optional<Vector2D> intersectRes = LineIntersectionUtil
                    .getLinesIntersections(e.getV1(), e.getV2(), bodyA.getCenter(), v);
            if (!intersectRes.isPresent()) continue;
            Vector2D p = intersectRes.get();
            if (LineIntersectionUtil.vertexInBound(p, v, bodyA.getCenter())) {
                Vector2D minusN = e.getN().scalarMultiply(-1);
                double nProj = (v.subtract(p)).dotProduct(minusN);
                return v.subtract(minusN.scalarMultiply(nProj)) ;
            }
        }
        throw new IllegalStateException("Could not find the closest point in the intersection");
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
