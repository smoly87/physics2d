package com.smoly.physics2d.core.scene;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.constraint.Constraint;
import com.smoly.physics2d.core.constraint.JointConstraint;
import com.smoly.physics2d.core.solver.Solver;
import com.smoly.physics2d.renderer.SceneRender;
import com.smoly.physics2d.core.utils.SceneDebuger;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Scene {
    public List<Body> getBodiesList() {
        return bodiesList;
    }

    private final  List<Body> bodiesList;
    private final List<Constraint> bodyInteractionsList;
    protected final SceneRender renderer;
    protected final Solver solver;
    protected final SceneDebuger sceneDebuger;

    public Scene(SceneRender renderer, Solver solver, SceneDebuger sceneDebuger) {
        bodiesList = new LinkedList<>();
        bodyInteractionsList = new LinkedList<>();
        this.renderer = renderer;
        this.solver = solver;
        this.sceneDebuger = sceneDebuger;
    }

    public void addBody(Body body) {
        this.bodiesList.add(body);
        if (body.getSubBodies().size() > 0) {
            this.bodiesList.addAll(body.getSubBodies());
        }
    }

    public void addJoint(JointConstraint jointConstraint) {
        this.bodyInteractionsList.add(jointConstraint);
    }

    public void start() throws InterruptedException {
        init();
        double t = 0;
        double maxT = 15;
        double dt = 1d/100d;
        while(t < maxT) {
            solver.step(bodiesList, bodyInteractionsList,dt );
            renderer.clear();
            renderer.renderBodies(bodiesList, Color.gray);
            renderer.renderBodies(sceneDebuger.getBodiesList(), Color.red);
            renderer.drawLabels(sceneDebuger.getPointsList());
            sceneDebuger.clearBodyList();
            t += dt;
           // break;
            Thread.sleep(10);
        }
    }

    protected void step() {

    }

    protected void init() {

    }

    public List<Constraint> getBodyInteractionsList() {
        return bodyInteractionsList;
    }
}
