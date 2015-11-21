/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.raypicking;

import eu.mihosoft.vrl.javaone2013.math.GeometryContainer;
import eu.mihosoft.vrl.javaone2013.math.MathUtil;
import eu.mihosoft.vrl.workflow.ConnectionEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.VNode;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class PlotterUIController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        glassPane.setMouseTransparent(true);
    }

    @FXML
    private void onAction(ActionEvent event) {
    }
    private VNode node;
    @FXML
    private Pane contentPane;
    private FunctionInput functionInput;
    @FXML
    private Pane glassPane;

    private GeometryContainer geometry;

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

        registerListener();
    }

    private void registerListener() {
        if (getNode() == null) {
            return;
        }

        if (getNode().getValueObject().getValue() == null) {
            return;
        }

        final Plotter3D plotter = (Plotter3D) getNode().getValueObject().getValue();

        final Connector c = getNode().getInputs().get(0);

        final ChangeListener<Number> propertyListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {

                geometry = plotter.plot(geometry, functionInput);
            }
        };

        c.addConnectionEventListener(new EventHandler<ConnectionEvent>() {
            @Override
            public void handle(ConnectionEvent t) {

                Connector sender = t.getSenderConnector();

                functionInput
                        = (FunctionInput) sender.getNode().
                        getValueObject().getValue();

                if (t.getEventType().equals(ConnectionEvent.REMOVE)) {
                    contentPane.getChildren().clear();
                    functionInput.aProperty().removeListener(propertyListener);
                    if (geometry != null) {
                        geometry.clear();
                    }
                } else {

                    geometry = plotter.plot(geometry, functionInput);

                    functionInput.aProperty().addListener(propertyListener);

                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(geometry);

                    AnchorPane.setTopAnchor(geometry, 0.0);
                    AnchorPane.setBottomAnchor(geometry, 0.0);
                    AnchorPane.setLeftAnchor(geometry, 0.0);
                    AnchorPane.setRightAnchor(geometry, 0.0);
                }
            }
        });

        getNode().getOutputs().get(0).addConnectionEventListener(new EventHandler<ConnectionEvent>() {
            @Override
            public void handle(ConnectionEvent event) {
                if (event.getEventType().equals(ConnectionEvent.ADD)) {
                    plotter.setSelectionReceiver(event.getReceiverConnector().getNode());
                } else if (event.getEventType().equals(ConnectionEvent.REMOVE)) {
                    plotter.setSelectionReceiver(null);
                }
            }
        });

    }
}
