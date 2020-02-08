package com.smoly.physics2d.core.solver;

import com.google.auto.value.AutoValue;
import com.smoly.physics2d.core.scene.ScreenConfig;

@AutoValue
public abstract class SolverConfig {
    public abstract double beta();
    public abstract int correctionStepsCount();
    public static SolverConfig.Builder builder() {
        return new AutoValue_SolverConfig.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract SolverConfig.Builder setBeta(double value);
        public abstract SolverConfig.Builder setCorrectionStepsCount(int value);
        public abstract SolverConfig build();
    }
}
