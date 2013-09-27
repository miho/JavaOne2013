/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * FXML based template silde (contains title and background).
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class TemplateSlide extends NodeSlide {

    private TemplateSlideController controller;

    /**
     * Constructor.
     */
    public TemplateSlide() {
//        setPresentation(presentation);

        initView();

    }

    /**
     * Returns the content pane of this template slide.
     * @return the content pane of this template slide
     */
    public Pane getContent() {
        return controller.getContent();
    }

    /**
     * Initializes the view (loads fxml, defines style).
     */
    private void initView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TemplateSlide.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(eu.mihosoft.vrl.workflow.demo.Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        Parent p = fxmlLoader.getRoot();

        controller = fxmlLoader.getController();

        controller.setSlide(this);

        final ScalableContentPane scalableContentPane = new ScalableContentPane();
        scalableContentPane.setAutoRescale(true);

        scalableContentPane.getStyleClass().setAll("slide-background");

        StackPane pane = new StackPane();

        scalableContentPane.setContentPane(pane);
        scalableContentPane.getContentPane().getChildren().add(p);
        setView(scalableContentPane);
    }

    @Override
    public void setTitleColor(Color c) {
        controller.titleLabel.setStyle("-fx-text-fill: rgba("
                + (int) (c.getRed() * 255) + ", "
                + (int) (c.getGreen() * 255) + ", "
                + (int) (c.getBlue() * 255) + ");");
    }
}
