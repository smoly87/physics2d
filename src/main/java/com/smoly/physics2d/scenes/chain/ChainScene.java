package com.smoly.physics2d.scenes.chain;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.Force;
import com.smoly.physics2d.core.Joint;
import com.smoly.physics2d.core.Scene;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class ChainScene extends Scene {
    @Override
    protected void init() {
        super.init();
        Body anchor = createAnchor();
        this.addBody(anchor);

        Body prevBody = anchor;
        double l = 0.70710678118/2;
        for(int i = 0; i < 1; i++) {
            Body piece = createChainPiece(i,l, Math.PI / 4);
            this.addBody(piece);
            this.addJoint(new Joint(prevBody, piece, 1,0 ));
            prevBody = piece;
        }
    }

    protected Body createChainPiece( int i, double l, double phi) {
        Body piece = new Body();
        double L = (i * l) + l/2;
        piece.setPosition(new Vector3D(L * cos(phi),L * sin(phi),phi));
        ArrayList<Vector2D> anchorVert = new ArrayList<>();
        anchorVert.add(new Vector2D(-l/2, 0));
        anchorVert.add(new Vector2D(l/2, 0));

        List<Force> forcesList = new ArrayList<>();
        forcesList.add(new Force(new Vector2D(0d,0d), new Vector2D(0d, -0.08)));
        piece.setForces(forcesList);
        piece.setVertexes(anchorVert);
        return piece;
    }

    protected Body createAnchor() {
        Body anchor = new Body();
        double l = 0.70710678118/2;
        anchor.setPosition(new Vector3D(0,0,0));
        ArrayList<Vector2D> anchorVert = new ArrayList<>();
        anchorVert.add(new Vector2D(-0.5*l, 0f));
        anchorVert.add(new Vector2D(0d, 0d));
        anchorVert.add(new Vector2D(0.5*l, 0f));
        anchor.setVertexes(anchorVert);
        anchor.setM(0);
        anchor.setI(0);
        return anchor;
    }
}
