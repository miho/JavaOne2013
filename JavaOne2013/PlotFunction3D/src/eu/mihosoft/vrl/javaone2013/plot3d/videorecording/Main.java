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
package eu.mihosoft.vrl.javaone2013.plot3d.videorecording;

import eu.mihosoft.vrl.workflow.Connector;
import eu.mihosoft.vrl.workflow.FlowFactory;
import eu.mihosoft.vrl.workflow.VFlow;
import eu.mihosoft.vrl.workflow.VNode;
import eu.mihosoft.vrl.workflow.fx.FXValueSkinFactory;
import eu.mihosoft.vrl.workflow.fx.VCanvas;
import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Main extends Application {

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with
     * limited FX
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
                new FunctionInput("sin(0.5*a*x)+cos(0.5*a*y)", 80, 1));
        VNode functionNode2 = createInputNode(flow, "Input 2",
                new FunctionInput("sin(a*sqrt(x*x+y*y))", 80, 1));
        functionNode2.setY(400);

        VNode plotter1 = createPlotter3DNode(flow, "3D Plotter");
        plotter1.setX(350);
        plotter1.setY(150);

        VNode selection = createSelectionMeasurementNode(flow, "Selection");
        selection.setX(1000);
        selection.setY(150);
        
        VNode node = createVideoNode(flow, "Video Player");

        // show the main stage/window
        showFlow(flow, primaryStage, "JavaOne 2013");
    }

    private static VNode createInputNode(VFlow flow, String title, FunctionInput input) {
        VNode functionNode = flow.newNode();
        functionNode.setWidth(300);
        functionNode.setHeight(320);
        functionNode.setTitle(title);
        functionNode.getValueObject().setValue(input);

        Connector output = functionNode.addOutput("data");

        return functionNode;
    }

    private static VNode createPlotter3DNode(VFlow flow, String title) {
        VNode plotterNode = flow.newNode();
        plotterNode.setWidth(600);
        plotterNode.setHeight(400);
        plotterNode.setTitle(title);
        plotterNode.getValueObject().setValue(new Plotter3D());

        Connector input = plotterNode.addInput("data");
        Connector output = plotterNode.addOutput("data");

        return plotterNode;
    }

    private static VNode createSelectionMeasurementNode(VFlow flow, String title) {
        VNode plotterNode = flow.newNode();
        plotterNode.setWidth(300);
        plotterNode.setHeight(140);
        plotterNode.setTitle(title);
        plotterNode.getValueObject().setValue(new SelectionResult());

        Connector input = plotterNode.addInput("data");

        return plotterNode;
    }
    
     private static VNode createVideoNode(VFlow flow, String title) {
         
        VNode videoNode = flow.newNode();
        videoNode.setWidth(300);
        videoNode.setHeight(320);
        videoNode.setTitle(title);
        videoNode.getValueObject().setValue(new VideoPlayer());

        return videoNode;
    }

    public static Pane embedSlide() {
        // create a new flow object
        VFlow flow = FlowFactory.newFlow();

        // make it visible
        flow.setVisible(true);

        VNode functionNode = createInputNode(flow, "Input 1",
                new FunctionInput("sin(0.5*a*x)+cos(0.5*a*y)", 80, 1));
        VNode functionNode2 = createInputNode(flow, "Input 2",
                new FunctionInput("sin(a*sqrt(x*x+y*y))", 80, 1));
        functionNode2.setY(400);

        VNode plotter1 = createPlotter3DNode(flow, "Plotter");
        plotter1.setX(600);
        plotter1.setY(150);


        // create scalable root pane
        VCanvas canvas = new VCanvas();
        canvas.setTranslateToMinNodePos(false);
        canvas.setMinScaleX(0.2);
        canvas.setMinScaleY(0.2);
        canvas.setMaxScaleX(1.0);
        canvas.setMaxScaleY(1.0);

        canvas.getStylesheets().setAll(
                "/eu/mihosoft/vrl/javaone2013/plot3d/resources/default.css");

        // define it as background (css class)
        canvas.getStyleClass().setAll("vflow-background");

        // create skin factory for flow visualization
        FXValueSkinFactory fXSkinFactory = new FXValueSkinFactory(canvas.getContentPane());

        // register visualizations for FunctionInput and Plotter3D
        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, InputFunctionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Plotter3D.class, Plotter3DFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(SelectionResult.class, SelectionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(VideoPlayer.class, VideoPlayerFlowNodeSkin.class);

        // generate the ui for the flow
        flow.addSkinFactories(fXSkinFactory);

        return canvas;
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

        PointLight pointLight1 = new PointLight();
        pointLight1.setColor(Color.ANTIQUEWHITE.darker().darker());
        pointLight1.translateXProperty().bind(canvas.widthProperty());
        pointLight1.setTranslateY(0);
        pointLight1.setTranslateZ(-1000);
        PointLight pointLight2 = new PointLight();
        pointLight2.setColor(Color.ANTIQUEWHITE.darker().darker());
        pointLight2.setTranslateX(0);
        pointLight2.setTranslateY(0);
        pointLight2.setTranslateZ(-1000);
        AmbientLight ambientLight = new AmbientLight(Color.ANTIQUEWHITE.darker().darker().darker());
        canvas.getChildren().addAll(pointLight1, pointLight2, ambientLight);


        // create skin factory for flow visualization
        FXValueSkinFactory fXSkinFactory = new FXValueSkinFactory(canvas.getContentPane());

        // register visualizations for FunctionInput and Plotter3D
        fXSkinFactory.addSkinClassForValueType(FunctionInput.class, InputFunctionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(Plotter3D.class, Plotter3DFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(SelectionResult.class, SelectionFlowNodeSkin.class);
        fXSkinFactory.addSkinClassForValueType(VideoPlayer.class, VideoPlayerFlowNodeSkin.class);

        // generate the ui for the flow
        flow.addSkinFactories(fXSkinFactory);

        // the usual application setup
        Scene scene = new Scene(canvas, 1024, 600);

        scene.setCamera(new PerspectiveCamera());

        // add css style
        scene.getStylesheets().setAll(
                "/eu/mihosoft/vrl/javaone2013/plot3d/resources/default.css");

        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
}
///**
// *
// * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
// */
//public class Main extends Application {
//
//    @Override
//    public void start(Stage stage) {
//
//        // create scalable root pane
//        ScalableContentPane canvas = new ScalableContentPane();
//
//        canvas.getStyleClass().setAll("slide-background");
//       
////        canvas.getContentPane().getChildren().add(createChart(new SineFunction1D(), 0, 10, 80));
//
//        // define background style
//        canvas.setStyle("-fx-background-color: white;");
//
//        // create skin factory for flow visualization
//        FXSkinFactory fXSkinFactory = new FXSkinFactory(canvas.getContentPane());
//
//
//        // the usual application setup
//        Scene scene = new Scene(canvas, 1024, 600);
//
////        scene.getStylesheets().setAll("/eu/mihosoft/vrl/javaone2013/resources/dark.css");
//
//        stage.setTitle("2D Demo");
//        stage.setScene(scene);
//        stage.show();
//
//    }
//
//    public Node createChart(Function1D f, double minX, double maxX, double res) {
//
//        //defining the axes
//        final NumberAxis xAxis = new NumberAxis();
//        final NumberAxis yAxis = new NumberAxis();
//        xAxis.setLabel("x");
//        yAxis.setLabel("y");
//        //creating the chart
//        final LineChart<Number, Number> lineChart =
//                new LineChart<Number, Number>(xAxis, yAxis);
//
//        lineChart.setTitle("sin(0.3*t^2)");
//        //defining a series
//        XYChart.Series series = new XYChart.Series();
//        series.setName("f(x)");
//        //populating the series with data
////        series.getData().add(new XYChart.Data(1, 23));
//
//        double dist = maxX -minX;
//
//        double dx =  dist/res;
//        
//        double x = minX;
//        
//        for(int i = 0; i < res; i++) {
//            
//            double y = f.run(x);
//            
//            series.getData().add(new XYChart.Data(x, y));
//            
//            x+=dx;
//        }
//        
//        lineChart.setCreateSymbols(false);
//        
//
//        lineChart.getData().add(series);
//
//        return lineChart;
//
//    }
//}