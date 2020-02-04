package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.Edge;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.List;
import java.util.stream.Collectors;

public class PenetrationTester {
    protected Body bodyA;
    protected Body bodyB;


    public List<PenetrationInfo> getPenetrationsList(Body b1, Body b2) {
        List<PenetrationInfo> vertsInside = b1.getVertexesAbs().stream()
                .filter(v -> isVertexInsideBody(v, b2))
                .map(v -> new PenetrationInfo(b1 ,b2, v, getClosestEdgePB(v, b2)))
                .collect(Collectors.toList());
        return vertsInside;
    }

    protected Vector2D getClosestEdgePB(Vector2D v, Body b) {
        double minD = 100000000;
        Edge minEdge = null;
        for( Edge e : b.getEdgesAbs()) {
            double x1 = e.getV1().getX();
            double x2 = e.getV2().getX();
            double y1 = e.getV1().getY();
            double y2 = e.getV2().getY();
            double A = x2 - x1;
            double B = y2 - y1;
            double C = -(A*x1 + B*y1);
            double d = Math.abs(A*v.getX() + B*v.getY() + C) / Math.sqrt(A * A + B * B);
            if(d < minD) {
                minD = d;
                minEdge = e;
            }
        }
        Vector2D k = new Vector2D(minEdge.getV2().getX() - minEdge.getV1().getX(), minEdge.getV2().getY() - minEdge.getV1().getY())
                .normalize()
                .scalarMultiply(minD);
        double nKoof = k.dotProduct(minEdge.getN());
        return b.getCenter().add(minEdge.getN().scalarMultiply(nKoof)) ; // pB
    }

    protected boolean isVertexInsideBody(Vector2D v, Body b) {
        for(Edge e : b.getEdgesAbs()) {
            double x1 = e.getV1().getX();
            double x2 = e.getV2().getX();
            double y1 = e.getV1().getY();
            double y2 = e.getV2().getY();

            double x0 = v.getX();
            double y0 = v.getY();

            double A = x2 - x1;
            double B = y2 - y1;
            double C = -(A*x0 + B*y0);
            double xp = ((B*B*x1)/A - B* y1 - C) / (A + B*B/A);
            double yp = (xp - x1) * (B/A) + y1;
            Vector2D p = new Vector2D(xp, yp);
            // If projection to normal for at least of one edge is positive hence the vertex is outside the body
            if (p.dotProduct(e.getN()) > 0) {
                return false;
            }

        }
        return true;
    }
}
