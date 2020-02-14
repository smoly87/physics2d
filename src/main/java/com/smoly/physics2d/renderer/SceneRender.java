package com.smoly.physics2d.renderer;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.core.utils.CanvasPointWithLabel;

import java.awt.*;
import java.util.List;

public interface SceneRender {
    public void renderBodies(List<Body> bodiesList, Color color);
    public void drawLabels(List<CanvasPointWithLabel> pointsList) ;
    public void redraw();
    public void clear();
}
