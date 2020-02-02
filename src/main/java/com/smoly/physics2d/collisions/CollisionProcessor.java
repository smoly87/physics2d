package com.smoly.physics2d.collisions;

import com.smoly.physics2d.DynamicConstraintsProcessor;
import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.BodyInteraction;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CollisionProcessor extends DynamicConstraintsProcessor {
    protected List<Body> bodies;
    protected Map<Body, BoundRect> boundRectMap;
    protected PenetrationTester penetrationTester;

    public CollisionProcessor(List<Body> bodiesList) {
        super(bodiesList);
        penetrationTester = new PenetrationTester();
    }

    protected List<CollisionCandidate> getCollisionCandidates() {
       List<BoundRect> boundRectsList =  bodies.stream()
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
    public List<BodyInteraction> generateDynamicInteraction() {
        return getCollisionCandidates().stream()
                .map(collisionCandidate -> penetrationTester.getPenetrationsList(collisionCandidate.bodyA, collisionCandidate.bodyB))
                .flatMap(Collection::stream)
                .map(penetrationInfo -> new Collision(penetrationInfo.getBodyA(), penetrationInfo.getBodyB(), penetrationInfo.pA,penetrationInfo.pB ))
                .collect(Collectors.toList());
    }
}
