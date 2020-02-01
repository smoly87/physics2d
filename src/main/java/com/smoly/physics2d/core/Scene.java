package com.smoly.physics2d.core;

import com.smoly.physics2d.renderer.Java2DRenderer;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    public List<Body> getBodiesList() {
        return bodiesList;
    }

    public List<BodyInteraction> getBodyInteractionsList() {
        return bodyInteractionsList;
    }

    private final  List<Body> bodiesList;
    private final List<BodyInteraction> bodyInteractionsList;
    public Scene() {
        bodiesList = new LinkedList<>();
        bodyInteractionsList = new LinkedList<>();
    }

    public void addBody(Body body) {
        this.bodiesList.add(body);
    }

    public void addJoint(Joint joint) {
        this.bodyInteractionsList.add(joint);
    }

    public void start() {
        init();
    }

    protected void init() {

    }

}
