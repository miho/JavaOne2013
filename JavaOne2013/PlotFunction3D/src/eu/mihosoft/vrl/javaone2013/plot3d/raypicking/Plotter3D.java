/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.raypicking;

import eu.mihosoft.vrl.javaone2013.math.Function2D;
import eu.mihosoft.vrl.javaone2013.math.GeometryContainer;
import eu.mihosoft.vrl.javaone2013.math.GroovyFunction2D;
import eu.mihosoft.vrl.javaone2013.math.MathUtil;
import eu.mihosoft.vrl.javaone2013.math.VFX3DUtil;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.NodeUtil;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.TriangleMesh;

/**
 * 3D function plotter.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Plotter3D {

    private VNode node;
    private final float scaleX = 12;
    private final float scaleY = 12;
    private final float scaleZ = 12;
    private Point3D selectedPoint;
    private Double selectedValue;
    private VNode selectionReceiver;
    private final PickingEvtHandler evtHandler = new PickingEvtHandler();

    public GeometryContainer plot(GeometryContainer prevView, FunctionInput functionInput) {
        //
        final GroovyFunction2D function
                = new GroovyFunction2D(functionInput.getFunction());

        function.setProperty("a", functionInput.getA());

        TriangleMesh mesh = MathUtil.evaluateFunction(
                function,
                functionInput.getResolution(), functionInput.getResolution(),
                scaleX, scaleY, scaleZ, -10, -10, 10, 10);

        if (prevView != null) {
            prevView.removeEventHandler(MouseEvent.ANY, evtHandler);
        }

        evtHandler.setFunction(function);

        GeometryContainer geometry = VFX3DUtil.meshToParent(prevView, mesh,
                MouseEvent.ANY, evtHandler);

        evtHandler.updateValue(null, true);

        return geometry;
    }

    /**
     * @return the node
     */
    public VNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(VNode node) {
        this.node = node;
    }

    void setSelectionReceiver(VNode node) {

        if (this.selectionReceiver != null) {
            this.selectionReceiver.getValueObject().
                    setValue(new SelectionResult());
        }

        this.selectionReceiver = node;
    }

    class PickingEvtHandler implements EventHandler<MouseEvent> {

        private Circle c = new Circle(10);
        private Function2D function;

        public PickingEvtHandler() {
            c.setFill(new Color(0.0, 1.0, 0.2, 0.5));
            c.setStroke(new Color(1.0, 1.0, 1.0, 0.4));
            c.setStrokeWidth(2);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(c.radiusProperty(), c.getRadius())),
                    new KeyFrame(Duration.millis(800),
                            new KeyValue(c.radiusProperty(), c.getRadius() * 2.5)));
            
            timeline.setAutoReverse(true);
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }

        @Override
        public void handle(MouseEvent event) {
            selectedPoint = event.getPickResult().getIntersectedPoint();

            if (event.getEventType() != MouseEvent.MOUSE_CLICKED) {
                return;
            }

            if (event.getButton() != MouseButton.SECONDARY) {
                return;
            }

            if (!(event.getSource() instanceof Group)) {
                return;
            }

            final Group group = (Group) event.getSource();

            if (group.getParent() == null) {
                return;
            }

            updateValue(group, false);
        }

        /**
         * @return the function
         */
        public Function2D getFunction() {
            return function;
        }

        /**
         * @param function the function to set
         */
        public void setFunction(Function2D function) {
            this.function = function;
        }

        private void updateValue(Group group, boolean scaled) {
            
            c.toFront();

            if (selectedPoint == null) {
                return;
            }                  

            if (c.getParent() != group && group != null) {
                NodeUtil.addToParent(group, c);
            }

            if (!scaled) {
                
                c.setTranslateX(selectedPoint.getX());
                c.setTranslateY(selectedPoint.getY());
                c.setTranslateZ(selectedPoint.getZ());
                
                selectedPoint = new Point3D(
                        selectedPoint.getX() / scaleX,
                        selectedPoint.getY() / scaleY,
                        selectedPoint.getZ() / scaleZ);
            }

//            System.out.println("point: " + selectedPoint);
            if (selectedPoint != null) {
                selectedValue = getFunction().run(selectedPoint.getX(),
                        selectedPoint.getY());

                if (selectionReceiver != null) {
                    selectionReceiver.getValueObject().setValue(
                            new SelectionResult(
                                    selectedPoint, selectedValue));
                }

//                System.out.println("value = " + selectedValue);
            } else {
                selectedValue = null;
            }
        }
    }
}
