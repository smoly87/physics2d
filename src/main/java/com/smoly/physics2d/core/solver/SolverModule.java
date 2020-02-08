package com.smoly.physics2d.core.solver;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import com.smoly.physics2d.core.DynamicConstraintsProcessor;
import com.smoly.physics2d.core.collisions.CollisionProcessor;
import com.smoly.physics2d.core.constraint.JointConstraint;

public class SolverModule extends AbstractModule {
    @Override
    protected void configure() {
        Multibinder.newSetBinder(binder(), DynamicConstraintsProcessor.class).addBinding().to(CollisionProcessor.class);
    }


}
