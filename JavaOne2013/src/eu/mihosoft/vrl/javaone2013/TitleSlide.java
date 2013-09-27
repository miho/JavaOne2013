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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class TitleSlide extends NodeSlide {

    private TitleSlideController controller;

    public TitleSlide(String title) {
        initView();
        setTitle(title);
    }
    
    

    private void initView() {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TitleSlide.fxml"));

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
