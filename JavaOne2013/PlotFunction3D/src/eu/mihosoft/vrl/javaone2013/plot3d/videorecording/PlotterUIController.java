/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.videorecording;

import eu.mihosoft.vrl.ext.com.bric.qt.JPEGMovieAnimation;
import eu.mihosoft.vrl.javaone2013.math.GeometryContainer;
import eu.mihosoft.vrl.media.VideoCreator;
import eu.mihosoft.vrl.workflow.ConnectionEvent;
import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.VNode;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
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
    @FXML
    private Button recordBtn;

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

    @FXML
    private void onRecordPressed(ActionEvent e) {

        recordBtn.setText("recording...");
        recordBtn.setDisable(true);

        try {
            final JPEGMovieAnimation anim = VideoCreator.create(new File("test.m4v"));
            
            String originalTitle = node.getTitle();

            AnimationTimer timer = new AnimationTimer() {

                long prev = 0;
                long runtime = 0;

                WritableImage img = new WritableImage(
                        (int) contentPane.getWidth(),
                        (int) contentPane.getHeight());

                @Override
                public void handle(long now) {
                    long dist;

                    if (prev > 0) {
                        dist = now - prev;
                    } else {
                        dist = 0;
                        prev = now;
                    }

                    if (dist > 0.1 * 1e9) {
                        prev = now;
                        runtime += dist;

                        SnapshotParameters params = new SnapshotParameters();
                        params.setCamera(new PerspectiveCamera());
                        img = contentPane.snapshot(params, img);

                        VideoCreator.addFrame(anim, img, 0.1f);
                        
                        node.setTitle("Recording: remaining " + (int)(10-runtime*1e-9));
                    }

                    if (runtime * 1e-9 > 10) {

                        try {
                            anim.close();
                        } catch (IOException ex) {
                            Logger.getLogger(PlotterUIController.class.getName()).
                                    log(Level.SEVERE, null, ex);
                        }
                        stop();
                        System.out.println(">> recording done at: " + runtime * 1e-9 + " sec");
                        recordBtn.setText("Record");
                        recordBtn.setDisable(false);
                        
                        node.setTitle(originalTitle);
                    }
                }
            };

            timer.start();

        } catch (IOException ex) {
            Logger.getLogger(PlotterUIController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

}
