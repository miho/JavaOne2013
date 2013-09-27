/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import eu.mihosoft.vrl.ide.RedirectableStream;
import eu.mihosoft.vrl.javaone2013.Slide;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import eu.mihosoft.vrl.ide.VCodeEditor;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class GroovyShellSlideController2 implements Initializable {

    @FXML
    private AnchorPane root;
    private Slide slide;
    private VCodeEditor editor;
    @FXML
    private TextArea outputArea;
    @FXML
    private AnchorPane editorPane;
    @FXML
    private Button runBtn;
    @FXML
    private Button resetBtn;
    private RedirectableStream serr;
    @FXML
    private TextField textField1;
    @FXML
    private TextField textField2;
    @FXML
    private TextField textField3;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editor = new VCodeEditor();

        editorPane.getChildren().add(editor);

        AnchorPane.setTopAnchor(editor, 0.0);
        AnchorPane.setBottomAnchor(editor, 0.0);
        AnchorPane.setLeftAnchor(editor, 0.0);
        AnchorPane.setRightAnchor(editor, 0.0);

        editor.setText(
                "// create a groovy shell for parsing the source string\n"
                + "\n\n"
                + "// parse the script (returns a script object)\n"
                + "\n\n"
                + "// set parameters\n\n"
                + "\n"
                + "// run the script\n"
                + "\n"
                + "// return the result\n"
                + "\n");

        editor.setBlendMode(BlendMode.MULTIPLY);



        runBtn.toFront();
        resetBtn.toFront();
    }

    public void setSlide(Slide s) {
        this.slide = s;

        s.getView().parentProperty().addListener(new ChangeListener<Parent>() {
            @Override
            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
                
                if (newValue == null) {
                    return;
                }
                
                // redirect sout
                RedirectableStream sout = new RedirectableStream(RedirectableStream.ORIGINAL_SOUT, outputArea);
                sout.setRedirectToUi(true);
                System.setOut(sout);

                // redirect serr
                serr = new RedirectableStream(RedirectableStream.ORIGINAL_SERR, outputArea);
                serr.setRedirectToUi(false);
                System.setErr(serr);
            }
        });
    }

    @FXML
    private void onCompile(ActionEvent e) {
        outputArea.setText("");
        System.out.println("----------------------------------\n>> compiling...");
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(editor.getText());
        script.setProperty("textField1", textField1);
        script.setProperty("textField2", textField2);
        script.setProperty("textField3", textField3);
        System.out.println("----------------------------------\n>> runing...");
        script.run();
    }

    @FXML
    private void onReset(ActionEvent e) {
        editor.setText(
                "// create a groovy shell for parsing the source string\n"
                + "GroovyShell shell = new GroovyShell();\n\n"
                + "// parse the script (returns a script object)\n"
                + "Script script = shell.parse(\"result=\"+textField2.getText());\n\n"
                + "// set parameters\n\n"
                + "script.setProperty(\"a\",textField1.getText() as Double)\n"
                + "// run the script\n"
                + "script.run();\n"
                + "// return the result\n"
                + "textField3.setText(script.getProperty(\"result\") as String)\n");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serr.setRedirectToUi(true);
                outputArea.setText("");
            }
        });

    }
}
