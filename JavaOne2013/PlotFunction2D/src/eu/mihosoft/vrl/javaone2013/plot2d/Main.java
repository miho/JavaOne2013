/*
 * Copyright 2012 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXValueSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Main extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // create a new flow object
        VFlow flow = FlowFactory.newFlow();

        // make it visible
        flow.setVisible(true);

        VNode functionNode = createInputNode(flow, "Input 1",
                new FunctionInput("x*x+a*sin(x*3)", 80, 1));
        VNode functionNode2 = createInputNode(flow, "Input 2",
                new FunctionInput("sin(x*a)", 160, 1));
        functionNode2.setY(400);
        
        VNode plotter1 = createPlotterNode(flow, "Plotter");
        plotter1.setX(600);
        plotter1.setY(180);
        
        createPlotterNode(flow, "Plotter");

        // show the main stage/window
        showFlow(flow, primaryStage, "VWorkflows Tutorial 05: View 1");
    }

    private static VNode createInputNode(VFlow flow, String title, FunctionInput input) {
        VNode functionNode = flow.newNode();
        functionNode.setWidth(300);
        functionNode.setHeight(380);
        functionNode.setTitle(title);
        functionNode.getValueObject().setValue(input);

        Connector output = functionNode.addOutput("data");

        return functionNode;
    }
    
    private static VNode createPlotterNode(VFlow flow, String title) {
        VNode plotterNode = flow.newNode();
        plotterNode.setWidth(600);
        plotterNode.setHeight(400);
        plotterNode.setTitle(title);
        plotterNode.getValueObject().setValue(new Plotter2D());

    
        Connector input = plotterNode.addInput("data");
        


        return plotterNode;
    }

    private void showFlow(VFlow flow, Stage stage, String title) {

        // create scalable root pane
        VCanvas canvas = new VCanvas();
        canvas.setTranslateToMinNodePos(false);
        canvas.setMinScaleX(0.5);
        canvas.setMinScaleY(0.5);
        canvas.setMaxScaleX(1.0);
        canvas.setMaxScaleY(1.0);

        // define it as background (css class)
        canvas.getStyleClass().setAll("vflow-background");

        // create skin factory for flow visualization
        FXValueSkinFactory fXSkinFactory = new FXValueSkinFactory(canvas.getContentPane());

        // register visualizations for Integer, String and Image
        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, InputFunctionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Plotter2D.class, Plotter2DFlowNodeSkin.class);

        // generate the ui for the flow
        flow.addSkinFactories(fXSkinFactory);

        // the usual application setup
        Scene scene = new Scene(canvas, 1024, 600);

        // add css style
        scene.getStylesheets().setAll(
                "/eu/mihosoft/vrl/javaone2013/plot2d/resources/default.css");

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    
    public static Pane embedSlide() {
        // create a new flow object
        VFlow flow = FlowFactory.newFlow();

        // make it visible
        flow.setVisible(true);

        VNode functionNode = createInputNode(flow, "Input 1",
                new FunctionInput("x*x+a*sin(x*3)", 80, 1));
        
        VNode functionNode2 = createInputNode(flow, "Input 2",
                new FunctionInput("sin(x*a)", 160, 1));
        
        functionNode2.setY(400);
        
        VNode plotter1 = createPlotterNode(flow, "Plotter");
        plotter1.setX(600);
        plotter1.setY(180);

        return showFlow(flow);
    }
    
    public static Pane embedSimpleSlide() {
        // create a new flow object
        VFlow flow = FlowFactory.newFlow();

        // make it visible
        flow.setVisible(true);
        
        VNode functionNode1 = createInputNode(flow, "Input",
                new FunctionInput("sin(x*a)", 160, 1));
        
        functionNode1.setX(50);
        functionNode1.setY(80);
        
        VNode plotter1 = createPlotterNode(flow, "Plotter");
        plotter1.setX(600);
        plotter1.setY(80);

        return showFlow(flow);
    }
    
    private static VCanvas showFlow(VFlow flow) {

        // create scalable root pane
        VCanvas canvas = new VCanvas();
        canvas.setTranslateToMinNodePos(false);
        canvas.setMinScaleX(0.5);
        canvas.setMinScaleY(0.5);
        canvas.setMaxScaleX(1.0);
        canvas.setMaxScaleY(1.0);

        // define it as background (css class)
        canvas.getStyleClass().setAll("vflow-background");

        // create skin factory for flow visualization
        FXValueSkinFactory fXSkinFactory = new FXValueSkinFactory(canvas.getContentPane());

        // register visualizations for Integer, String and Image
        fXSkinFactory.addSkinClassForValueType(Integer.class, IntegerFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, InputFunctionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Plotter2D.class, Plotter2DFlowNodeSkin.class);

        // generate the ui for the flow
        flow.addSkinFactories(fXSkinFactory);

        // add css style
        canvas.getStylesheets().setAll(
                "/eu/mihosoft/vrl/javaone2013/plot2d/resources/default.css");

        return canvas;
    }
}
