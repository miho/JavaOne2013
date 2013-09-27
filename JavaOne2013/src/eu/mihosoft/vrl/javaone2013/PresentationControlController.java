/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import eu.mihosoft.vrl.javaone2013.ext.animation.FadeInTransition;
import eu.mihosoft.vrl.javaone2013.ext.animation.FadeOutTransition;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * FXML controller class for the visual presentation control.
 * 
 * It contains the code that is responsible for creating the "milk glass"
 * effect.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class PresentationControlController implements Initializable {

    private FadeInTransition fadeIn;
    private FadeOutTransition fadeOut;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //
        blurView.fitWidthProperty().bind(root.widthProperty());

        final Rectangle rect = new Rectangle();

        root.setClip(rect);

        rect.widthProperty().bind(root.widthProperty());
        rect.heightProperty().bind(root.heightProperty());

        root.setOpacity(0.0);

        rect.setY(root.getPrefHeight() - 20);

        root.onMouseExitedProperty().setValue(
                (EventHandler<MouseEvent>) (MouseEvent t) -> {
                    if (fadeOut == null) {

                        fadeOut = new FadeOutTransition(root, 500, 500);
                        fadeOut.play();
                        fadeOut.onFinishedProperty().setValue(
                                (EventHandler<ActionEvent>) (ActionEvent t2) -> {
                                    fadeOut = null;

                                    rect.setY(root.getHeight() - 20);
                                });
                    }
                });

        root.onMouseEnteredProperty().setValue(
                (EventHandler<MouseEvent>) (MouseEvent t) -> {
                    if (getPresentation().showsIndex()) {
                        return;
                    }

                    if (fadeIn == null) {

                        rect.setY(0);

                        if (fadeOut != null) {
                            fadeOut.stop();
                            fadeOut = null;
                        }

                        if (root.getOpacity() < 1.0) {
                            fadeIn = new FadeInTransition(root, 0, 500);
                            fadeIn.play();
                            fadeIn.onFinishedProperty().setValue(
                                    (EventHandler<ActionEvent>) (ActionEvent t2) -> {
                                        fadeIn = null;
                                    });
                        }
                    }
                });
    }
    private Presentation presentation;
    @FXML
    Button prevSlideBtn;
    @FXML
    Button nextSlideBtn;
    @FXML
    Button indexBtn;
    @FXML
    ImageView blurView;
    @FXML
    Pane root;

    @FXML
    void onPrevSlide(ActionEvent e) {
        if (root.getOpacity() == 0.0) {
            return;
        }

        getPresentation().getActionController().prev();
    }

    @FXML
    void onNextSlide(ActionEvent e) {
        if (root.getOpacity() == 0.0) {
            return;
        }

        getPresentation().getActionController().next();
    }

    @FXML
    void onIndexSlide(ActionEvent e) {
        if (root.getOpacity() == 0.0) {
            return;
        }
        getPresentation().showIndex();
    }

    /**
     * @return the presentation
     */
    public Presentation getPresentation() {
        return presentation;
    }

    /**
     * @param presentation the presentation to set
     */
    public void setPresentation(Presentation presentation) {
        this.presentation = presentation;
    }
}
