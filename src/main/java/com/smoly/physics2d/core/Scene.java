package com.smoly.physics2d.core;

import com.smoly.physics2d.core.constraint.Constraint;
import com.smoly.physics2d.core.constraint.JointConstraint;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public List<Body> getBodiesList() {
        return bodiesList;
    }

    public List<Constraint> getBodyInteractionsList() {
        return bodyInteractionsList;
    }

    private final  List<Body> bodiesList;
    private final List<Constraint> bodyInteractionsList;
    public Scene() {
        bodiesList = new LinkedList<>();
        bodyInteractionsList = new LinkedList<>();
    }

    public void addBody(Body body) {
        this.bodiesList.add(body);
    }

    public void addJoint(JointConstraint jointConstraint) {
        this.bodyInteractionsList.add(jointConstraint);
    }

    public void start() {
        init();
    }

    protected void init() {

    }

}
