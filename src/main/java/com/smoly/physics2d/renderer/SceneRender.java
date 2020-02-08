package com.smoly.physics2d.renderer;

import com.smoly.physics2d.core.Body;

import java.awt.*;
import java.util.List;

public interface SceneRender {
    public void renderBodies(List<Body> bodiesList, Color color);
    public void clear();
}
