/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.javaone2013;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class TitleSlideController implements Initializable {
    @FXML
    private AnchorPane root;
    @FXML
    private StackPane content;
    @FXML
     Label titleLabel;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public void setSlide(Slide s) {
        titleLabel.textProperty().bind(s.titleProperty());
    }

    /**
     * @return the content
     */
    public Pane getContent() {
        return content;
    }
    
}
