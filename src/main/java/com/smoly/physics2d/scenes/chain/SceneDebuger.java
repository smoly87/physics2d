package com.smoly.physics2d.scenes.chain;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smoly.physics2d.core.Body;

import java.util.ArrayList;
import java.util.List;
@Singleton
public class SceneDebuger {
    public List<Body> getBodiesList() {
        return bodiesList;
    }

    private  List<Body> bodiesList;

    @Inject
    public SceneDebuger() {
        clearBodyList();
    }

    public void clearBodyList() {
        bodiesList = new ArrayList<>();
    }

    public void addBody(Body body) {
        bodiesList.add(body);
    }
    public void addBody(List<Body> bodiesList) {
        this.bodiesList.addAll(bodiesList);
    }

}
