/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
        // TODO
    }
    @FXML
    private TextField functionTextField;
    @FXML
    private TextField resolutionTextField;

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
        this.resolutionTextField.setText(""+input.getResolution());
        
    }
}
