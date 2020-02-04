package com.smoly.physics2d.core.constraint;

import com.smoly.physics2d.core.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import static com.smoly.physics2d.core.MatrixUtils.crossProduct2d;
public class JointConstraint extends Constraint {


    protected int bodyAVertInd;
    protected int bodyBVertInd;

    public JointConstraint(Body bodyA, Body bodyB, int bodyAVertInd, int bodyBVertInd) {
        super(bodyA, bodyB);
        this.bodyAVertInd = bodyAVertInd;
        this.bodyBVertInd = bodyBVertInd;
        this.setConstraintType(ConstraintType.EQUALITY);

    }

    @Override
    public RealMatrix getJ() {
        Vector2D pA = getBodyA().getAbsCoord(getBodyA().getVertexes().get(bodyAVertInd));
        Vector2D pB = getBodyB().getAbsCoord(getBodyB().getVertexes().get(bodyBVertInd));
        Vector2D cA = getBodyA().getCenter();
        Vector2D cB = getBodyB().getCenter();

        double[] J = new double[]{
                pB.getX() - pA.getX(),
                pB.getY() - pA.getY(),
                crossProduct2d(pA.subtract(pB),  pB.subtract(cB)),
                pA.getX() - pB.getX(),
                pA.getY() - pB.getY(),
                crossProduct2d(pB.subtract(pA),  pA.subtract(cA)),
        };
        return new Array2DRowRealMatrix(J).transpose().scalarMultiply(2);

    }

    @Override
    public double getBias() {
        Vector2D pA = getBodyA().getAbsCoord(getBodyA().getVertexes().get(bodyAVertInd));
        Vector2D pB = getBodyB().getAbsCoord(getBodyB().getVertexes().get(bodyBVertInd));
        Vector2D c = pA.subtract(pB);
        return c.dotProduct(c);
    }
}
