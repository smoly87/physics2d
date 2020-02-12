package com.smoly.physics2d.scenes.chain;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.BodyBuidler;
import com.smoly.physics2d.core.geometry.Force;
import com.smoly.physics2d.core.geometry.bodies.types.SimpleGeometryFactory;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.core.solver.Solver;
import com.smoly.physics2d.core.utils.SceneDebuger;
import com.smoly.physics2d.renderer.SceneRender;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class FallingBoxesScene extends Scene {
    @Inject
    public FallingBoxesScene(SceneRender renderer, Solver solver, SceneDebuger sceneDebuger) {
        super(renderer, solver,sceneDebuger);
    }

    @Override
    protected void init() {
       this.addBody(createBox(new Vector3D(0.0,1.5,0.25)));
        this.addBody(createBox(new Vector3D(1.5,1.5,0.35)));

        this.addBody(createFloor());
    }

    protected Body createBox(Vector3D position) {
        return   BodyBuidler.newBuilder()
                .setPosition(position) //0.8581499999999982
                .addAllVertex(SimpleGeometryFactory.getQuadVertexes())
                .addForce(new Vector2D(0,0), new Vector2D(0,-.1))
                .build();
    }

    protected Body createFloor() {
        return   BodyBuidler.newBuilder()
                .setPosition(new Vector3D(0.0,-0,0))
                .addAllVertex(SimpleGeometryFactory.getRectangleVertexes(5,0.5))
                .setInvMass(0)
                .setInvI(0)
                .build();

    }
}
