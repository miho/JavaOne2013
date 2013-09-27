/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import eu.mihosoft.vrl.javaone2013.math.GroovyFunction1D;
import eu.mihosoft.vrl.javaone2013.math.MathUtil;
import eu.mihosoft.vrl.workflow.Connection;
import eu.mihosoft.vrl.workflow.ConnectionEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.VNode;
import java.net.URL;
import java.util.Collection;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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
        // TODO
    }

    @FXML
    private void onAction(ActionEvent event) {
    }
    private VNode node;
    @FXML
    private Pane contentPane;

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

        final Plotter plotter = (Plotter) getNode().getValueObject().getValue();

        final Connector c = getNode().getInputs().get(0);

        c.addConnectionEventListener(new EventHandler<ConnectionEvent>() {
            @Override
            public void handle(ConnectionEvent t) {

                if (t.getEventType().equals(ConnectionEvent.REMOVE)) {
                    contentPane.getChildren().clear();
                } else {

                    Connector sender = t.getSenderConnector();

                    FunctionInput functionInput =
                            (FunctionInput) sender.getNode().
                            getValueObject().getValue();

                    Node chart = plotter.plot(functionInput);

                    contentPane.getChildren().clear();
                    contentPane.getChildren().add(chart);

                    AnchorPane.setTopAnchor(chart, 0.0);
                    AnchorPane.setBottomAnchor(chart, 0.0);
                    AnchorPane.setLeftAnchor(chart, 0.0);
                    AnchorPane.setRightAnchor(chart, 0.0);
                }
            }
        });

    }
}
