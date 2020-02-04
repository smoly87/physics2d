package com.smoly.physics2d.core.collisions;

import com.google.inject.Inject;
import com.smoly.physics2d.core.DynamicConstraintsProcessor;
import com.smoly.physics2d.core.constraint.CollisionConstraint;
import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.constraint.Constraint;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollisionProcessor extends DynamicConstraintsProcessor {
    protected Map<Body, BoundRect> boundRectMap;
    protected final PenetrationTester penetrationTester;

    @Inject
    public CollisionProcessor(PenetrationTester penetrationTester) {
        this.penetrationTester = penetrationTester;
    }

    protected List<CollisionCandidate> getCollisionCandidates(List<Body> bodiesList) {
       List<BoundRect> boundRectsList =  bodiesList.stream()
               .map(body -> createBoundRect(body))
               .collect(Collectors.toList());
        List<CollisionCandidate> collisionCandidateList = new LinkedList<>();

        for (BoundRect b1: boundRectsList) {
            for (BoundRect b2:boundRectsList) {
                if(b1.equals(b2)) continue;
                if (b1.isIntersectsWith(b2)) {
                    collisionCandidateList.add(new CollisionCandidate(b1.getBody(), b2.getBody()));
                }
            }
        }
        return collisionCandidateList;
    }

    protected BoundRect createBoundRect(Body body) {
        double Xmin = body.getVertexesAbs().stream().map(v -> v.getX()).min(Double::compareTo).get();
        double Ymin = body.getVertexesAbs().stream().map(v -> v.getY()).min(Double::compareTo).get();
        double Xmax = body.getVertexesAbs().stream().map(v -> v.getY()).max(Double::compareTo).get();
        double Ymax = body.getVertexesAbs().stream().map(v -> v.getY()).max(Double::compareTo).get();
        return new BoundRect(body, Xmin, Ymin, Xmax - Xmin, Ymax - Ymin);
    }


    @Override
    public List<Constraint> generateDynamicConstraints(List<Body> bodiesList) {
        return getCollisionCandidates(bodiesList).stream()
                .map(collisionCandidate -> penetrationTester.getPenetrationsList(collisionCandidate.bodyA, collisionCandidate.bodyB))
                .flatMap(Collection::stream)
                .map(penetrationInfo -> new CollisionConstraint(penetrationInfo.getBodyA(), penetrationInfo.getBodyB(), penetrationInfo.pA,penetrationInfo.pB ))
                .collect(Collectors.toList());
    }
}
