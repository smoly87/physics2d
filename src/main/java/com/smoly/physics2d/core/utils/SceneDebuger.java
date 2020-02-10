package com.smoly.physics2d.core.utils;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.smoly.physics2d.core.geometry.Body;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
@Singleton
public class SceneDebuger {
    public List<Body> getBodiesList() {
        return bodiesList;
    }

    private List<Body> bodiesList;

    public List<CanvasPointWithLabel> getPointsList() {
        return pointsList;
    }

    private  List<CanvasPointWithLabel> pointsList;

    @Inject
    public SceneDebuger() {
        clearBodyList();
    }

    public void clearBodyList() {
        bodiesList = new ArrayList<>();
        pointsList = new ArrayList<>();
    }

    public void addBody(Body body) {
        bodiesList.add(body);
    }
    public void addBody(List<Body> bodiesList) {
        this.bodiesList.addAll(bodiesList);
    }

    public void addPoint(CanvasPointWithLabel canvasPointWithLabel) {
        pointsList.add(canvasPointWithLabel);
    }


}
