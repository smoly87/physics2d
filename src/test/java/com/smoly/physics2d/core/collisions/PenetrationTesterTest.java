package com.smoly.physics2d.core.collisions;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.utils.SceneDebuger;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PenetrationTesterTest {
    PenetrationTester penetrationTester;
    @Before
    public void setUp() throws Exception {
        penetrationTester = new PenetrationTester(new SceneDebuger());
    }

    @Test
    public void getClosestEdgePB() {
      /*  Body bodyA = new Body();
        bodyA.setPosition(new Vector3D(-1, -.5,0d) );

        Body bodyB = new Body();
        List<Vector2D> bodyBVert = new ArrayList<>();
        bodyBVert.add(new Vector2D(-1,-1));
        bodyBVert.add(new Vector2D(1,1));
        bodyB.setVertexes(bodyBVert);

        Vector2D res = penetrationTester.getClosestEdgePB(new Vector2D(1,0.5), bodyA, bodyB);
        assertEquals(0.5, res.getX(), 1e-12);
        assertEquals(0.5, res.getY(), 1e-12);*/

    }


}