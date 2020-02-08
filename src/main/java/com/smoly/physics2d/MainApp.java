package com.smoly.physics2d;

// Java Program to create a to create
// a canvas and paint the canvas
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.smoly.physics2d.core.scene.Scene;
import com.smoly.physics2d.renderer.Java2DRenderer;
import com.smoly.physics2d.scenes.chain.ChainScene;
import com.smoly.physics2d.scenes.chain.TestHelperScene;

class MainApp {
    // Main Method
    public static void main(String args[]) {
        Injector injector = Guice.createInjector(new MainModule());
        Scene scene = injector.getInstance(TestHelperScene.class);
        scene.start();
    }
}

