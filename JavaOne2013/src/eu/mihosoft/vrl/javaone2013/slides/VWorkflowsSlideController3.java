/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import eu.mihosoft.vrl.ide.RedirectableStream;
import eu.mihosoft.vrl.ide.VCodeEditor;
import eu.mihosoft.vrl.javaone2013.Slide;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VWorkflowsSlideController3 implements Initializable {

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
    @FXML
    private AnchorPane demoPane;

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
                "// show plotter\n\n");

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
        serr.setRedirectToUi(true);
        outputArea.setText("");
        System.out.println("----------------------------------\n>> compiling...");
        GroovyShell shell = new GroovyShell();
        Script script = shell.parse(
                "import eu.mihosoft.vrl.workflow.*;\n"
                + "import eu.mihosoft.vrl.workflow.fx.*;\n\n" + editor.getText());
        
        demoPane.getStyleClass().setAll("vflow-background");

        demoPane.getChildren().clear();
        Pane p = eu.mihosoft.vrl.javaone2013.plot2d.Main.embedSlide();
        demoPane.getChildren().add(p);
    }

    @FXML
    private void onReset(ActionEvent e) {
        editor.setText("// show plotter");

        Platform.runLater(() -> {
            serr.setRedirectToUi(true);
            outputArea.setText("");
        });

    }
}
