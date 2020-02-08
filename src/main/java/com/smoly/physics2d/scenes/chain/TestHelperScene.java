package com.smoly.physics2d.scenes.chain;

import com.google.inject.Inject;
import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.renderer.SceneRender;
import com.smoly.physics2d.core.solver.Solver;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;

import java.util.stream.Collectors;

public class TestHelperScene extends Scene {
    @Inject
    public TestHelperScene(SceneRender renderer, Solver solver, SceneDebuger sceneDebuger) {
        super(renderer, solver,sceneDebuger);
    }

    @Override
    protected void init() {
        super.init();
        QuadBody bodyA = new QuadBody();
        bodyA.setPosition(new Vector3D(0.8,0.5,0));
        Body bodyB = new QuadBody();
        bodyB.setPosition(new Vector3D(0,0,0));

        addNormals(bodyA);
        this.addBody(bodyA);
        this.addBody(bodyB);
    }

    protected void addNormals(Body bodyA) {
        sceneDebuger.addBody(
                bodyA.getEdgesAbs()
                        .stream()
                        .map(edge -> new VectorVis(edge.getMiddle(), edge.getN()))
                        .collect(Collectors.toList()));
    }




}
