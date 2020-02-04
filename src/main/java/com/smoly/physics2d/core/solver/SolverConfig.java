package com.smoly.physics2d.core.solver;

public class SolverConfig {
    private final double beta;
    private final int correctionStepsCount;

    public SolverConfig(double beta, int correctionStepsCount) {
        this.beta = beta;
        this.correctionStepsCount = correctionStepsCount;
    }

    public double getBeta() {
        return beta;
    }

    public int getCorrectionStepsCount() {
        return correctionStepsCount;
    }
}
