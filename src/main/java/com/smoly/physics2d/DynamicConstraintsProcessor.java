package com.smoly.physics2d;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.BodyInteraction;

import java.util.List;

public abstract class DynamicConstraintsProcessor {
    protected List<Body> bodiesList;
    public DynamicConstraintsProcessor( List<Body> bodiesList) {
        this.bodiesList = bodiesList;
    }
    public abstract List<BodyInteraction>  generateDynamicInteraction();
}
