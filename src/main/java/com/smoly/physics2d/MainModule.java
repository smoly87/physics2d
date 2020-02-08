package com.smoly.physics2d;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.smoly.physics2d.core.scene.ScreenConfig;
import com.smoly.physics2d.core.solver.SolverConfig;
import com.smoly.physics2d.core.solver.SolverModule;
import com.smoly.physics2d.renderer.Java2DRenderer;
import com.smoly.physics2d.renderer.SceneRender;

import java.awt.*;

public class MainModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new SolverModule());
        bind(SceneRender.class).to(Java2DRenderer.class);
    }

    @Provides
    ScreenConfig provideScreenConfig() {
        return ScreenConfig.builder()
                .setScreenWidth(1000)
                .setScreenHeight(1000)
                .setxMin(-5d)
                .setxMax(5d)
                .setyMin(-5d)
                .setyMax(5d)
                .build();
    }

    @Provides
    SolverConfig providesSolverConfig() {
        return SolverConfig.builder().setBeta(0.2).setCorrectionStepsCount(4).build();
    }
}
