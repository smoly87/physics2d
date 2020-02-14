package com.smoly.physics2d.core.utils;



import static com.smoly.physics2d.core.utils.MathUtils.isCloseTo;
import static java.lang.Math.min;
import static java.lang.Math.max;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import java.util.Optional;

public class LineIntersectionUtil {


    /**
     *  Determines whether defined vertex lies between two specified vertexes.
     * @param v
     * @param v1
     * @param v2
     * @return
     */
    public static boolean vertexInBound(Vector2D v, Vector2D v1, Vector2D v2) {
        double D = 1e-2;
        return ((v.getX()  >= min(v1.getX(), v2.getX()) && v.getX()  <= max(v1.getX(), v2.getX()))
                && (v.getY() >= min(v1.getY(), v2.getY()) && v.getY() <= max(v1.getY(), v2.getY()))
        );
    }

    /**
     * Determines whether two lines defined by the two points have intersection.
     * @param line1v1
     * @return
     */
    public static Optional<Vector2D> getLinesIntersections(Vector2D line1v1, Vector2D line1v2, Vector2D line2v1, Vector2D line2v2) {
        double x1 = line1v1.getX();
        double x2 = line1v2.getX();
        double y1 = line1v1.getY();
        double y2 = line1v2.getY();

        double x3 = line2v1.getX();
        double x4 = line2v2.getX();
        double y3 = line2v1.getY();
        double y4 =  line2v2.getY();

        double p1 = (x2-x1);
        double p2 = (x4-x3);
        double q1 = (y2-y1);
        double q2 = (y4-y3);



        if (isCloseTo(q1, 0 ) && isCloseTo(q2, 0 )) {
            Vector2D vec;
            if (line1v2.subtract(line1v2).getNorm() < line2v2.subtract(line2v2).getNorm()) {
                vec = line1v2.subtract(line1v2).scalarMultiply(0.5);
            } else {
                vec = line2v2.subtract(line2v2).scalarMultiply(0.5);
            }
            return Optional.of(vec);
        }
        double xp;
        double yp;
        if (isCloseTo(p1, 0 )) {
            xp = x1;
            yp = lineValueY(line2v1, line2v2, xp);
            return Optional.of(new Vector2D(xp, yp));
        }
        if (isCloseTo(q1, 0 )) {
            yp = y1;
            xp = lineValueX(line2v1, line2v2, yp);
            return Optional.of(new Vector2D(xp, yp));
        }
        if (isCloseTo(p2, 0 )) {
            xp = x3;
            yp = lineValueY(line1v1, line1v2, xp);
            return Optional.of(new Vector2D(xp, yp));
        }
        if (isCloseTo(q2, 0 )) {
            yp = y3;
            xp = lineValueX(line1v1, line1v2, yp);
            return Optional.of(new Vector2D(xp, yp));
        }

        xp = ((q2/p2) * x3 - (x1*q1)/p1 + y1 -y3) / (q2/p2 - q1/p1);
        yp = (xp - x1) * q1/p1 + y1;
        return Optional.of(new Vector2D(xp, yp));
    }

    protected static double lineValueY(Vector2D linev1, Vector2D linev2, double x) {
        double x1 = linev1.getX();
        double x2 = linev2.getX();
        double y1 = linev1.getY();
        double y2 = linev2.getY();
        double p = (x2-x1);
        double q = (y2-y1);

        return (x - x1) *q /p + y1;
    }

    protected static double lineValueX(Vector2D line1v1, Vector2D line1v2, double y) {
        double x1 = line1v1.getX();
        double x2 = line1v2.getX();
        double y1 = line1v1.getY();
        double y2 = line1v2.getY();
        double p = (x2-x1);
        double q = (y2-y1);

        return (y - y1) *p /q + x1;
    }
}
