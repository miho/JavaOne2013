/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import eu.mihosoft.vrl.javaone2013.Slide;
import eu.mihosoft.vrl.javaone2013.plot3d.raypicking.Main;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Plotter3DSlideController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane plotterContent;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        plotterContent.getChildren().add(Main.embedSlide());
    }

    public void setSlide(Slide s) {
        //
    }

}
