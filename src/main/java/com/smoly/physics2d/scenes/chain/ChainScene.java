package com.smoly.physics2d.scenes.chain;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.Force;
import com.smoly.physics2d.core.constraint.JointConstraint;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.core.utils.SceneDebuger;
import com.smoly.physics2d.renderer.SceneRender;
import com.smoly.physics2d.core.solver.Solver;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

public class ChainScene extends Scene {
    @Inject
    public ChainScene(SceneRender renderer, Solver solver, SceneDebuger sceneDebuger) {
        super(renderer, solver, sceneDebuger);
    }

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
            this.addJoint(new JointConstraint(prevBody, piece, 1,0 ));
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
        anchor.setInvM(0);
        anchor.setInvI(0);
        return anchor;
    }
}
