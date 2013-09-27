/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

/**
 * Slide that displays fxml content.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class FXMLSlide extends TemplateSlide {

    /**
     * Location of the fxml resource path
     *
     * @param fxmlPath path
     */
    public FXMLSlide(String fxmlPath) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(fxmlPath));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(eu.mihosoft.vrl.workflow.demo.Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        Parent p = fxmlLoader.getRoot();

//        StackPane.setAlignment(p, Pos.CENTER);
        getContent().getChildren().add(p);
        try {
            Method m
                    = fxmlLoader.getController().getClass().getMethod("setSlide", Slide.class);
            m.invoke(fxmlLoader.getController(), this);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NullPointerException ex) {
            Logger.getLogger(FXMLSlide.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
