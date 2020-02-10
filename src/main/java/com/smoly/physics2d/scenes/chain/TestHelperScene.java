package com.smoly.physics2d.scenes.chain;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.geometry.Force;
import com.smoly.physics2d.core.geometry.bodies.types.QuadBody;
import com.smoly.physics2d.core.geometry.bodies.types.VectorVis;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.core.utils.SceneDebuger;
import com.smoly.physics2d.renderer.SceneRender;
import com.smoly.physics2d.core.solver.Solver;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.util.ArrayList;
import java.util.List;
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
         List<Force> forcesBodyA = new ArrayList<Force>();
        forcesBodyA.add(new Force(new Vector2D(0,0), new Vector2D(0,-.1)));
        bodyA.setForces(forcesBodyA);
        Body bodyB = new QuadBody();
        bodyB.setPosition(new Vector3D(0,0,0));
       // addNormals(bodyA);
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
