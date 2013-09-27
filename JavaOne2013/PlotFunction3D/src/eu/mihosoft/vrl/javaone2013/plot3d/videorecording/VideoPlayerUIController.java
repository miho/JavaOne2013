/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.videorecording;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VideoPlayerUIController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Pane contentPane;
    @FXML
    private Pane glassPane;
    @FXML
    private Button playBtn;

    private MediaPlayer player;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

    @FXML
    private void onPlayPressed(ActionEvent event) {
        
        contentPane.getChildren().clear();

        String mediaURL = "";
        try {
            mediaURL = new File("test.m4v").toURI().toURL().toExternalForm();
        } catch (MalformedURLException ex) {
            Logger.getLogger(VideoPlayerUIController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Media m = new Media(mediaURL);
        player = new MediaPlayer(m);
        MediaView mv = new MediaView(player);

        mv.fitWidthProperty().bind(contentPane.widthProperty());
        mv.fitHeightProperty().bind(contentPane.heightProperty());
        
//        mv.translateXProperty().bind(contentPane.widthProperty().divide(2));

        contentPane.getChildren().add(mv);

//        AnchorPane.setTopAnchor(mv, 0.0);
//        AnchorPane.setBottomAnchor(mv, 0.0);
//        AnchorPane.setLeftAnchor(mv, 0.0);
//        AnchorPane.setRightAnchor(mv, 0.0);

        player.play();
    }

}
