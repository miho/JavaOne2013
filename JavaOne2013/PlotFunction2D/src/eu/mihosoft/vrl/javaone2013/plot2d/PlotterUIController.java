/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d;

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
import javafx.scene.chart.LineChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import jdk.nashorn.internal.runtime.PropertyListener;

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
        // TODO
    }

    @FXML
    private void onAction(ActionEvent event) {
    }
    private VNode node;
    @FXML
    private Pane contentPane;
    private FunctionInput functionInput;

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

        final Plotter2D plotter = (Plotter2D) getNode().getValueObject().getValue();

        final Connector c = getNode().getInputs().get(0);

        final ChangeListener<Number> propertyListener = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                final LineChart<Number, Number> lineChart = MathUtil.createChart();
                lineChart.getData().clear();
                plotter.plot(functionInput, lineChart);

                contentPane.getChildren().clear();
                contentPane.getChildren().add(lineChart);

                AnchorPane.setTopAnchor(lineChart, 0.0);
                AnchorPane.setBottomAnchor(lineChart, 0.0);
                AnchorPane.setLeftAnchor(lineChart, 0.0);
                AnchorPane.setRightAnchor(lineChart, 0.0);
            }
        };

        c.addConnectionEventListener(new EventHandler<ConnectionEvent>() {
            @Override
            public void handle(ConnectionEvent t) {

                Connector sender = t.getSenderConnector();

                functionInput =
                        (FunctionInput) sender.getNode().
                        getValueObject().getValue();

                if (t.getEventType().equals(ConnectionEvent.REMOVE)) {
                    contentPane.getChildren().clear();
                    functionInput.aProperty().removeListener(propertyListener);
                } else {
                    final LineChart<Number, Number> lineChart = MathUtil.createChart();

                    plotter.plot(functionInput, lineChart);

                    functionInput.aProperty().addListener(propertyListener);

                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(lineChart);

                    AnchorPane.setTopAnchor(lineChart, 0.0);
                    AnchorPane.setBottomAnchor(lineChart, 0.0);
                    AnchorPane.setLeftAnchor(lineChart, 0.0);
                    AnchorPane.setRightAnchor(lineChart, 0.0);
                }
            }
        });

    }
}
