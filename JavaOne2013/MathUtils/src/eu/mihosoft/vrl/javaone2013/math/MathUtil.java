/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

import java.util.List;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.shape.TriangleMesh;

/**
 * Untility class for evaluating 2D and 3D functions.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class MathUtil {

    private MathUtil() {
        throw new AssertionError("Don't instanciate me!");
    }

    /**
     * Creates a LineChart object with two number axes.
     *
     * @return linechart object
     */
    public static LineChart createChart() {

        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("x");
        yAxis.setLabel("y");

        //creating the chart
        LineChart<Number, Number> lineChart
                = new LineChart<>(xAxis, yAxis);
        lineChart.setCreateSymbols(false);

        return lineChart;

    }

    /**
     * Evaluates the specified 1D function and adds the data to a given
     * LineChart object.
     *
     * @param lineChart linechart that shall be used to visualize f
     * @param title chart title
     * @param f function that shall be evaluated
     * @param minX minimum x value
     * @param maxX maximum x value
     * @param res resolution (high resolution causes high plot density)
     * @return the linechart object that visualizes f
     */
    public static LineChart evaluateFunction(
            LineChart lineChart, String title,
            Function1D f, double minX, double maxX, double res) {

        lineChart.setTitle(title);

        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("f(x)");

        // evaluate function
        // dist between max and min
        double dist = maxX - minX;

        // step size
        double dx = dist / res;

        // let x start with minX
        double x = minX;

        // step through the interval
        for (int i = 0; i < res; i++) {

            // evaluate f at x
            double y = f.run(x);

            // add (x,y) to the series
            series.getData().add(new XYChart.Data(x, y));

            // prepare next step
            x += dx;
        } // end for i

        // add series to linechart
        lineChart.getData().add(series);

        return lineChart;

    }

    /**
     * Evaluates the specified 2D function and returns the data as TriangleMesh.
     *
     * @param f function that shall be evaluated
     * @param subDivX x resolution
     * @param subDivY y resolution
     * @param scaleX scale factor for x axis
     * @param scaleY scale factor for y axis
     * @param scaleZ scale factor for z axis
     * @param minX minimum x value
     * @param minY minimum y value
     * @param maxX maximum x value
     * @param maxY maximum y value
     * @return TriangleMesh
     */
    public static TriangleMesh evaluateFunction(
            Function2D f,
            int subDivX, int subDivY, float scaleX, float scaleY, float scaleZ,
            double minX, double minY, double maxX, double maxY) {

        // create the mesh
        TriangleMesh mesh = new TriangleMesh();

        // 3 point indices and 3 texCoord indices per triangle
        int numDivX = subDivX + 1;

        // // step through the interval (y axis)
        for (int y = 0; y <= subDivY; y++) {

            // compute stepsize in y direction
            float dy = (float) y / subDivY;

            // compute second function parameter
            double fy = (1 - dy) * minY + dy * maxY;

            // step through the interval (x axis)
            for (int x = 0; x <= subDivX; x++) {

                // compute stepsize in x direction
                float dx = (float) x / subDivX;

                // compute first function parameter
                double fx = (1 - dx) * minX + dx * maxX;

                // create points
                mesh.getPoints().addAll((float) fx * scaleX);
                mesh.getPoints().addAll((float) fy * scaleY);
                mesh.getPoints().addAll((float) (-f.run(fx, fy) * scaleZ));

                // add corresponding texture coordinates
                // (not covered in this tutorial)
                mesh.getTexCoords().addAll(dx);
                mesh.getTexCoords().addAll(dy);
            }
        }

        // Points have been created. Now we triangulate them.
        // (taken from OpenJFX sample code)
        for (int y = 0; y < subDivY; y++) {
            for (int x = 0; x < subDivX; x++) {

                // compute indices for points and texture coordinates
                int p00 = y * numDivX + x;
                int p01 = p00 + 1;
                int p10 = p00 + numDivX;
                int p11 = p10 + 1;
                int tc00 = y * numDivX + x;
                int tc01 = tc00 + 1;
                int tc10 = tc00 + numDivX;
                int tc11 = tc10 + 1;

                // define faces (first triangle)
                mesh.getFaces().addAll(p00);
                mesh.getFaces().addAll(tc00);
                mesh.getFaces().addAll(p10);
                mesh.getFaces().addAll(tc10);
                mesh.getFaces().addAll(p11);
                mesh.getFaces().addAll(tc11);

                // define faces (second triangle)
                mesh.getFaces().addAll(p11);
                mesh.getFaces().addAll(tc11);
                mesh.getFaces().addAll(p01);
                mesh.getFaces().addAll(tc01);
                mesh.getFaces().addAll(p00);
                mesh.getFaces().addAll(tc00);
            }
        }

        return mesh;
    }

    /**
     * Converts the specified triangle list to a TriangleMesh.
     *
     * @param triangles
     * @param scaleX scale factor for x axis
     * @param scaleY scale factor for y axis
     * @param scaleZ scale factor for z axis
     * @return TriangleMesh
     */
    public static TriangleMesh toTriangleMesh(
            List<Triangle> triangles, List<Node> nodes, float scaleX, float scaleY, float scaleZ) {

        // create the mesh
        TriangleMesh mesh = new TriangleMesh();

        for (Node n : nodes) {
            // create points
            mesh.getPoints().addAll((float) n.getPoint().getX() * scaleX);
            mesh.getPoints().addAll((float) n.getPoint().getY() * scaleY);
            mesh.getPoints().addAll((float) n.getPoint().getZ() * scaleZ);

            // add corresponding texture coordinates
            // (not covered in this tutorial)
            mesh.getTexCoords().addAll(0); // texture (not covered)
            mesh.getTexCoords().addAll(0); // texture (not covered)
        }

        // Points have been created. Now we triangulate them.
        // (taken from OpenJFX sanple code)
        for (Triangle t : triangles) {

            // compute indices for points and texture coordinates
            int p00 = t.getxIndex();
            int p01 = t.getyIndex();
            int p10 = t.getzIndex();

            // define face (triangle)
            mesh.getFaces().addAll(p00);
            mesh.getFaces().addAll(0); // texture (not covered)
            mesh.getFaces().addAll(p01);
            mesh.getFaces().addAll(0); // texture (not covered)
            mesh.getFaces().addAll(p10);
            mesh.getFaces().addAll(0); // texture (not covered)
        }

        return mesh;
    }

}
