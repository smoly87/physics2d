package com.smoly.physics2d.renderer;

import com.google.inject.Inject;
import com.smoly.physics2d.core.geometry.Body;
import com.smoly.physics2d.core.utils.CanvasPointWithLabel;
import com.smoly.physics2d.core.utils.MatrixUtils;
import com.smoly.physics2d.core.scene.ScreenConfig;
import org.apache.commons.math3.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math3.linear.RealMatrix;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.*;
import static com.smoly.physics2d.core.utils.MatrixUtils.*;
public class Java2DRenderer extends JFrame implements SceneRender {

    double xToViewPortCoof;
    double yToViewPortCoof;
    RealMatrix viewPortMatrix;
    protected long t;
    protected long startT;
    protected Canvas canvas;
    protected final ScreenConfig config;
    protected Integer[] worldCoordsToViewPort(Vector2D vec) {
      Vector3D viewPortCoord = new Vector3D(viewPortMatrix.multiply(vecToRealMatrix(createFrom2D(vec, 1d))).getColumn(0));
      return new Integer[]{(int)viewPortCoord.getX(), (int)viewPortCoord.getY()};
    }

    protected RealMatrix calculateViewPortMatrix() {
        xToViewPortCoof = (config.screenWidth()/(config.xMax() - config.xMin()));
        yToViewPortCoof = (config.screenHeight()/(config.yMax() - config.yMin()));
        RealMatrix A = MatrixUtils.diagRealMatrix(new double[]{xToViewPortCoof, -yToViewPortCoof, 0d });
        A.setEntry(0, 2, - config.xMin() * xToViewPortCoof);
        A.setEntry(1, 2, - config.yMin() * yToViewPortCoof);
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

    public void renderBodies(List<Body> bodiesList, Color color) {
        for(Body body : bodiesList) {
            Polygon polygon = createPolygonFromBody(body);
            Graphics g = canvas.getGraphics();
            g.setColor(color);
            g.drawPolygon(polygon);
            //g.drawOval();
        }
    }

    public void drawLabels(List<CanvasPointWithLabel> pointsList) {
        int d = 6;
        Graphics g = canvas.getGraphics();
        for(CanvasPointWithLabel canvasPointWithLabel:pointsList) {
            Integer[] p = worldCoordsToViewPort(canvasPointWithLabel.getPosition());
            g.setColor(canvasPointWithLabel.getColor());
            g.fillOval(
                    (int)(p[0] - d/2),
                    (int)(p[1] -d/2)
                    ,d,d );
        }

    }

    public void setColor(Color color) {
        canvas.getGraphics().setColor(Color.red);
    }

    @Override
    public void clear() {
        canvas.getGraphics().clearRect(0, 0, config.screenWidth(), config.screenHeight());
    }

    @Inject
    public Java2DRenderer(ScreenConfig config, Canvas canvas) {
        super("canvas");
        this.config = config;
        viewPortMatrix = calculateViewPortMatrix();
        canvas.setBackground(Color.black);
        add(canvas);
        setSize(config.screenWidth(), config.screenHeight());
        this.canvas = canvas;
        show();
    }
}
