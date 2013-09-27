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
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXSkinFactory;
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

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class VWorkflowsSlideController2 implements Initializable {

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
                "// create connectors\n\n"
                + "// connect n1 and n2\n\n");


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
        // add css style
        canvas.getStylesheets().setAll(
                "/eu/mihosoft/vrl/javaone2013/resources/default.css");
//        canvas.setContentPane(new Pane());

        demoPane.getChildren().add(canvas);
        AnchorPane.setTopAnchor(canvas, 0.0);
        AnchorPane.setBottomAnchor(canvas, 0.0);
        AnchorPane.setLeftAnchor(canvas, 0.0);
        AnchorPane.setRightAnchor(canvas, 0.0);

        VFlow flow = FlowFactory.newFlow();

        VNode n1 = flow.newNode();
        n1.setTitle("n1");

        VNode n2 = flow.newNode();
        n2.setTitle("n2");
        n2.setX(300);
        n2.setY(150);

        FXSkinFactory skinFactory = new FXSkinFactory(canvas.getContentPane());
        flow.addSkinFactories(skinFactory);
        flow.setVisible(true);

        script.setProperty("flow", flow);
        script.setProperty("n1", n1);
        script.setProperty("n2", n2);


        System.out.println("----------------------------------\n>> runing...");
        script.run();
        serr.setRedirectToUi(false);
        
//        FXSkinFactory skinFactory = new FXSkinFactory(canvas.getContentPane());
//        flow.setSkinFactories(skinFactory);
//        flow.setVisible(true);
    }

    @FXML
    private void onReset(ActionEvent e) {
        editor.setText(
                "// create connectors\n"
                + "Connector out1 = n1.addOutput('data');\n"
                + "Connector in1 = n2.addInput('data');\n\n"
                + "// connect n1 and n2\n"
                + "flow.connect(out1,in1);\n\n");

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                serr.setRedirectToUi(true);
                outputArea.setText("");
            }
        });

    }
}
