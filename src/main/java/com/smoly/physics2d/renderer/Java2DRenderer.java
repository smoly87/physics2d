package com.smoly.physics2d.renderer;

import com.smoly.physics2d.core.Body;
import com.smoly.physics2d.core.MatrixUtils;
import com.smoly.physics2d.core.Scene;
import com.smoly.physics2d.core.Solver;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import static com.smoly.physics2d.core.MatrixUtils.*;
public class Java2DRenderer extends JFrame {
    double Xmin = -5;
    double Xmax = 5;
    double Ymin = -5;
    double Ymax = 5;
    int ScreenWidth = 1000;
    int ScreenHeight = 1000;
    double xToViewPortCoof;
    double yToViewPortCoof;
    RealMatrix viewPortMatrix;
    protected long t;
    protected long startT;
    protected Integer[] worldCoordsToViewPort(Vector2D vec) {
        //System.out.println(vec);
        //return new Integer[] {(int) ((vec.getX() - Xmin) * xToViewPortCoof), (int) ((-vec.getY() - Ymin) * yToViewPortCoof)};
      Vector3D viewPortCoord = new Vector3D(viewPortMatrix.multiply(vecToRealMatrix(createFrom2D(vec, 1d))).getColumn(0));
      return new Integer[]{(int)viewPortCoord.getX(), (int)viewPortCoord.getY()};
    }

    protected RealMatrix calculateViewPortMatrix() {
        xToViewPortCoof = (ScreenWidth/(Xmax - Xmin));
        yToViewPortCoof = (ScreenHeight/(Ymax - Ymin));
        RealMatrix A = MatrixUtils.diagRealMatrix(new double[]{xToViewPortCoof, -yToViewPortCoof, 0d });
        A.setEntry(0, 2, - Xmin * xToViewPortCoof);
        A.setEntry(1, 2, - Ymin * yToViewPortCoof);
        return A;
    }

    protected Polygon createPolygonFromBody(Body body) {
        List<Integer[]> vertList = body
                .getVertexes()
                .stream()
                .map(vert -> body.getAbsCoord(vert))
                .map(vertAbsCoords -> worldCoordsToViewPort(vertAbsCoords))
                .collect(Collectors.toList());
        return new Polygon(
                vertList.stream().map(viewPortCoords -> viewPortCoords[0]).mapToInt(x -> x).toArray(),
                vertList.stream().map(viewPortCoords -> viewPortCoords[1]).mapToInt(y -> y).toArray(),
                vertList.size());
    }

    // constuctor
    public Java2DRenderer(final Scene scene) {
        super("canvas");
        viewPortMatrix = calculateViewPortMatrix();
        Solver solver = new Solver(scene.getBodiesList(), scene.getBodyInteractionsList());
        t = System.nanoTime();
        startT = t;
        scene.start();
        Canvas c = new Canvas() {
            public void paint(Graphics g) {
             /*   if ((t - startT) /1000 > 5){
                    return;
                }*/
                long dt = System.nanoTime() - t;
                t = System.nanoTime();
                solver.step((double)(0.1));
                //System.out.println(((double)dt/10e9));
                g.setColor(Color.red);
                for(Body body : scene.getBodiesList()) {
                    Polygon polygon = createPolygonFromBody(body);
                   g.drawPolygon(polygon);
               }
                this.repaint();

            }
        };

        c.setBackground(Color.black);
        add(c);
        setSize(ScreenWidth, ScreenHeight);
        show();
    }
}
