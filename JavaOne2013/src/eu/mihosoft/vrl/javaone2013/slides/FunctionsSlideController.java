/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import eu.mihosoft.vrl.javaone2013.Action;
import eu.mihosoft.vrl.javaone2013.Slide;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class FunctionsSlideController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private Font x1;
    @FXML
    private Color x2;
    @FXML
    private TextField functionInput;
    @FXML
    private TextField valueInput1;
    @FXML
    private TextField valueInput2;
    @FXML
    private TextField valueInput3;
    @FXML
    private TextField valueOutput1;
    @FXML
    private TextField valueOutput2;
    @FXML
    private TextField valueOutput3;
    private Slide slide;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void onChange(ActionEvent e) {

        String expression = functionInput.getText();

        GroovyShell shell = new GroovyShell();
        Script script = shell.parse("import static java.lang.Math.*; result = ("
                + expression + ") as Double");


        try {
            Double value1 = Double.parseDouble(valueInput1.getText());
            script.setProperty("x", value1);
            script.run();
            valueOutput1.setText(script.getProperty("result").toString());
        } catch (Exception ex) {
            Logger.getLogger(FunctionsSlideController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        try {
            Double value2 = Double.parseDouble(valueInput2.getText());
            script.setProperty("x", value2);
            script.run();
            valueOutput2.setText(script.getProperty("result").toString());
        } catch (Exception ex) {
             Logger.getLogger(FunctionsSlideController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
        try {
            Double value3 = Double.parseDouble(valueInput3.getText());
            script.setProperty("x", value3);
            script.run();
            valueOutput3.setText(script.getProperty("result").toString());
        } catch (Exception ex) {
             Logger.getLogger(FunctionsSlideController.class.getName()).
                    log(Level.SEVERE, null, ex);
        }
    }

    public void setSlide(Slide s) {
        this.slide = s;

        if (s == null) {
            return;
        }

        slide.addActions(new Action() {
            @Override
            public void perform() {
                functionInput.setText("x*x");
            }

            @Override
            public void undo() {
                functionInput.setText("x*x");
            }
        }, new Action() {
            @Override
            public void perform() {
                valueInput1.setText("1");
                onChange(null);
            }

            @Override
            public void undo() {
                valueInput1.setText("");
                valueOutput1.setText("");
            }
        }, new Action() {
            @Override
            public void perform() {
                valueInput2.setText("2");
                onChange(null);
            }

            @Override
            public void undo() {
                valueInput2.setText("");
                valueOutput2.setText("");
            }
        }, new Action() {
            @Override
            public void perform() {
                valueInput3.setText("3");
                onChange(null);
            }

            @Override
            public void undo() {
                valueInput3.setText("");
                valueOutput3.setText("");
            }
        });
    }
}
