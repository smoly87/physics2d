package com.smoly.physics2d;

// Java Program to create a to create
// a canvas and paint the canvas
import com.smoly.physics2d.renderer.Java2DRenderer;
import com.smoly.physics2d.scenes.chain.ChainScene;

import java.awt.*;
import javax.swing.*;
class MainApp {

    // Main Method
    public static void main(String args[])
    {
        Java2DRenderer r = new Java2DRenderer(new ChainScene());
    }
}

