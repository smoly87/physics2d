package com.smoly.physics2d.core.solver;

import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.constraint.Constraint;

import java.util.List;

public abstract class DynamicConstraintsProcessor {
    public abstract List<Constraint> generateDynamicConstraints(List<Body> bodiesList);
}
