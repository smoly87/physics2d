package com.smoly.physics2d.core.collisions;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.BodyBuidler;
import com.smoly.physics2d.core.geometry.bodies.types.SimpleGeometryFactory;
import com.smoly.physics2d.core.solver.DynamicConstraintsProcessor;
import com.smoly.physics2d.core.constraint.CollisionConstraint;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.constraint.Constraint;
import com.smoly.physics2d.core.utils.CanvasPointWithLabel;
import com.smoly.physics2d.core.utils.SceneDebuger;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionProcessor extends DynamicConstraintsProcessor {
    protected Map<Body, BoundRect> boundRectMap;
    protected final PenetrationTester penetrationTester;
    protected final SceneDebuger sceneDebuger;

    @Inject
    public CollisionProcessor(PenetrationTester penetrationTester, SceneDebuger sceneDebuger) {
        this.penetrationTester = penetrationTester;
        this.sceneDebuger = sceneDebuger;
    }

    protected List<CollisionCandidate> getCollisionCandidates(List<Body> bodiesList) {
       List<BoundRect> boundRectsList =  bodiesList.stream()
               .map(body -> createBoundRect(body))
               .collect(Collectors.toList());
        List<CollisionCandidate> collisionCandidateList = new LinkedList<>();

        for (int i = 0; i < boundRectsList.size(); i ++) {
            for (int j = i + 1 ; j < boundRectsList.size(); j++) {
                BoundRect b1 = boundRectsList.get(i);
                BoundRect b2 = boundRectsList.get(j);
                if (b1.isIntersectsWith(b2) || b2.isIntersectsWith(b1)) {
                    collisionCandidateList.add(new CollisionCandidate(b1.getBody(), b2.getBody()));
                    drawBoundBox(b1);
                    drawBoundBox(b2);
                }
            }
        }
        return collisionCandidateList;
    }

    private void drawBoundBox(BoundRect b) {
        sceneDebuger.addBody(BodyBuidler.newBuilder()
                .addAllVertex(SimpleGeometryFactory.getRectangleVertexes(b.width, b.height))
                .setPosition(new Vector3D(b.x + b.width/2, b.y + b.height/2,0))
                .build());
    }

    protected BoundRect createBoundRect(Body body) {
        double Xmin = body.getVertexesAbs().stream().map(v -> v.getX()).min(Double::compareTo).get();
        double Ymin = body.getVertexesAbs().stream().map(v -> v.getY()).min(Double::compareTo).get();
        double Xmax = body.getVertexesAbs().stream().map(v -> v.getX()).max(Double::compareTo).get();
        double Ymax = body.getVertexesAbs().stream().map(v -> v.getY()).max(Double::compareTo).get();
        return new BoundRect(body, Xmin, Ymin, Xmax - Xmin, Ymax - Ymin);
    }


    @Override
    public List<Constraint> generateDynamicConstraints(List<Body> bodiesList) {
        List<CollisionCandidate> collisionCandidates = getCollisionCandidates(bodiesList);
        List<PenetrationInfo> penetrationInfoListAB = collisionCandidates.stream()
                .map(collisionCandidate -> penetrationTester.getPenetrationsList(collisionCandidate.bodyA, collisionCandidate.bodyB))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<PenetrationInfo> penetrationInfoListBA = collisionCandidates.stream()
                .map(collisionCandidate -> penetrationTester.getPenetrationsList(collisionCandidate.bodyB, collisionCandidate.bodyA))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        List<PenetrationInfo> penetrationInfoList = new ArrayList<>();
        penetrationInfoList.addAll(penetrationInfoListAB);
        penetrationInfoList.addAll(penetrationInfoListBA);

        List<Constraint> res =
                penetrationInfoList
                        .stream()
                        .map(penetrationInfo -> new CollisionConstraint(penetrationInfo.getBodyA(), penetrationInfo.getBodyB(), penetrationInfo.pA,penetrationInfo.pB ))
                        .collect(Collectors.toList());
        debugConstraints(res);
        return res;
    }

    private void debugConstraints(List<Constraint> constraints) {
        for(Constraint constraint : constraints) {
            CollisionConstraint collisionConstraint = (CollisionConstraint)constraint;
            sceneDebuger.addPoint(new CanvasPointWithLabel(collisionConstraint.getpA(), Color.blue) );
            sceneDebuger.addPoint(new CanvasPointWithLabel(collisionConstraint.getpB(), Color.green) );
        }

    }
}
