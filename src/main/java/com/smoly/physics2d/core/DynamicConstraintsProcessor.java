package com.smoly.physics2d.core;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.constraint.Constraint;

import java.util.List;

public abstract class DynamicConstraintsProcessor {
    public abstract List<Constraint> generateDynamicConstraints(List<Body> bodiesList);
}
