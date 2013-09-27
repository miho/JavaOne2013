/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class InputUIController implements Initializable {

    private FunctionInput input;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // 
    }
    @FXML
    private TextField functionTextField;
    @FXML
    private TextField resolutionTextField;
    @FXML
    private Slider paramSlider;
    @FXML
    private TextField paramTextField;

    @FXML
    private void onAction(ActionEvent e) {
        if (input != null) {
            input.setFunction(functionTextField.getText());
            input.setResolution(Integer.parseInt(resolutionTextField.getText()));
        }
    }

    /**
     * @return the input
     */
    public FunctionInput getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(FunctionInput input) {

        this.input = input;

        this.functionTextField.setText(input.getFunction());
        this.resolutionTextField.setText("" + input.getResolution());

        paramSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                InputUIController.this.input.setA(newValue.doubleValue());
                
                DecimalFormat df = new DecimalFormat("0.00");
                
                paramTextField.setText(df.format(newValue.doubleValue()));
            }
        });



    }
}
