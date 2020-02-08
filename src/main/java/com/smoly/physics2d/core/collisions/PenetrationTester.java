package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.Edge;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import static java.lang.Math.min;
import static java.lang.Math.max;

import java.util.List;
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

    protected Vector2D getClosestEdgePB(Vector2D v,Body bodyA, Body bodyB) {
        for( Edge e : bodyB.getEdgesAbs()) {
            double x1 = e.getV1().getX();
            double x2 = e.getV2().getX();
            double y1 = e.getV1().getY();
            double y2 = e.getV2().getY();

            double x3 = v.getX();
            double x4 = bodyA.getCenter().getX();
            double y3 = e.getV1().getY();
            double y4 = bodyA.getCenter().getY();;

            double p1 = (x2-x1);
            double p2 = (x4-x3);
            double q1 = (y2-y1);
            double q2 = (y4-y3);
            double xp = ((q2/p2) * x3 - (x1*q1)/p1 + y1 -y3) / (q2/p2 - q1/p1);
            double yp = (xp - x1) * q1/p1 + y1;

            if ((xp >= min(x3, x4) && xp <= max(x3, x4)) && (yp >= min(y3, y4) && yp <= max(y3, y4))) {
                Vector2D p = new Vector2D(xp, yp);
                Vector2D minusN = e.getN().scalarMultiply(-1);
                double nProj = v.subtract(p).dotProduct(minusN);
                return v.add(minusN.scalarMultiply(nProj)) ;
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
