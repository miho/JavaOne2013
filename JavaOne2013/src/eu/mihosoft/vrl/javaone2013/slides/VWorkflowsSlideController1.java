/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import at.bestsolution.javafx.ide.editor.ContentProposalComputer;
import eu.mihosoft.vrl.ide.CodeProposal;
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
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.fx.ScalableContentPane;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import groovy.lang.GroovyShell;
import groovy.lang.Script;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VWorkflowsSlideController1 implements Initializable {

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
        
        editor.addProposal(new CodeProposal(ContentProposalComputer.Type.TYPE, "VNode", "VNode"));
        editor.addProposal(new CodeProposal(ContentProposalComputer.Type.TYPE, "VFlow", "VFlow"));
        editor.addProposal(new CodeProposal(ContentProposalComputer.Type.TYPE, "FlowFactory", "FlowFactory"));
        editor.addProposal(new CodeProposal(ContentProposalComputer.Type.TYPE, "FXSkinFactory", "FXSkinFactory"));

        editorPane.getChildren().add(editor);

        AnchorPane.setTopAnchor(editor, 0.0);
        AnchorPane.setBottomAnchor(editor, 0.0);
        AnchorPane.setLeftAnchor(editor, 0.0);
        AnchorPane.setRightAnchor(editor, 0.0);

        editor.setText(
                "// create flow object\n"
                + "\n\n"
                + "// create flow node\n"
                + "\n\n"
                + "// create another node\n"
                + "\n\n"
                + "// create JavaFX user interface\n"
                + "\n\n");


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

        demoPane.getChildren().clear();
        VCanvas canvas = new VCanvas();
        canvas.setTranslateToMinNodePos(false);
        canvas.setMaxScaleX(1);
        canvas.setMaxScaleY(1);
//        canvas.setStyle("-fx-border-color: black;");
        canvas.setContentPane(new Pane());
        demoPane.getChildren().add(canvas);
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);
        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);

        script.setProperty("canvas", canvas.getContentPane());

        System.out.println("----------------------------------\n>> runing...");
        script.run();
        serr.setRedirectToUi(false);
    }

    @FXML
    private void onReset(ActionEvent e) {
        editor.setText(
                "// create flow object\n"
                + "VFlow flow = FlowFactory.newFlow();\n\n"
                + "// create flow node\n"
                + "VNode n1 = flow.newNode();\n"
                + "n1.setTitle('n1');\n\n"
                + "// create another node\n"
                + "VNode n2 = flow.newNode();\n"
                + "n2.setTitle('n2');\n\n"
                + "// create JavaFX user interface\n"
                + "FXSkinFactory skinFactory = new FXSkinFactory(canvas);\n"
                + "flow.addSkinFactories(skinFactory)\n"
                + "flow.setVisible(true)\n");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serr.setRedirectToUi(true);
                outputArea.setText("");
            }
        });

    }
}
