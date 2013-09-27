/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.event.EventHandler;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class Main extends Application {

    private boolean navigationActive = true;

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
    public void start(Stage stage) {

        final Presentation presentation = new DefaultPresentation();

        Slide slide;

        presentation.addSlide(new ImageSlide("slide-first.png"));
        presentation.addSlide(new ImageSlide("slide-about-me.png"));
        presentation.addSlide(new ImageSlide("slide-outline.png"));

        presentation.addSlide(new TitleSlide("Why Choose JavaFX?"));
        presentation.addSlide(new ImageSlide("slide-why-choose-jfx-01.png"));
        presentation.addSlide(new ImageSlide("slide-why-choose-jfx-02.png"));
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/Controls2DSlide.fxml");
        slide.setTitle("Why Choose JavaFX?");
        presentation.addSlide(slide);

        presentation.addSlide(new ImageSlide("slide-why-choose-jfx-04.png"));
        presentation.addSlide(new HTMLSlide("http://javafxcommunity.com"));

        presentation.addSlide(new TitleSlide("Introduction To Functions"));
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/FunctionsSlide.fxml");
        slide.setTitle("Introduction To Functions");
        presentation.addSlide(slide);
        presentation.addSlide(new ImageSlide("slide-functions.png"));

        presentation.addSlide(new TitleSlide("Creating a 2D Plotter"));
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/Plotter2DSlide.fxml");
        slide.setTitle("Creating a 2D Plotter");
        presentation.addSlide(slide);
        presentation.addSlide(new ImageSlide("slide-creating-a-2d-function-plotter-02.png"));

        // groovy evaluation
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/GroovyShellSlide.fxml");
        slide.setTitle("Evaluating Expressions 1");
        presentation.addSlide(slide);
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/GroovyShellSlide2.fxml");
        slide.setTitle("Evaluating Expressions 2");
        presentation.addSlide(slide);
        presentation.addSlide(new TitleSlide("Demo / Code"));

        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/VWorkflowsSlide2.fxml");
        slide.setTitle("Workflows");
        
        presentation.addSlide(new ImageSlide("slide-creating-a-2d-function-plotter-03.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-creating-a-2d-function-plotter-04.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-creating-a-2d-function-plotter-05.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));

        presentation.addSlide(new TitleSlide("Creating a 3D Plotter"));
        slide = new FXMLSlide("/eu/mihosoft/vrl/javaone2013/slides/Plotter3DSlide.fxml");
        slide.setTitle("Creating a 3D Plotter");
        presentation.addSlide(slide);
        presentation.addSlide(new ImageSlide("slide-creating-a-3d-function-plotter-02.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-creating-a-3d-function-plotter-03.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-creating-a-3d-function-plotter-04.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-creating-a-3d-function-plotter-05.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));

        presentation.addSlide(new TitleSlide("Ray Picking & 3D Visualizations"));
        presentation.addSlide(new TitleSlide("Demo / Code"));

        presentation.addSlide(new TitleSlide("But It Does Move!"));
        presentation.addSlide(new ImageSlide("slide-but-it-moves-01.png"));
        presentation.addSlide(new TitleSlide("Demo / Code"));
        presentation.addSlide(new ImageSlide("slide-thanks.png"));
        presentation.addSlide(new ImageSlide("slide-q&a.png"));

        for (int i = 1; i < 8; i++) {
            Slide s = new TemplateSlide();
            presentation.addSlide(s);
        }

        Scene scene = new Scene(presentation.getView(), 1280, 720);
        PerspectiveCamera cam = new PerspectiveCamera();
        cam.setNearClip(0.0);
        cam.setFarClip(10.0);
        scene.setCamera(cam);

        scene.addEventFilter(KeyEvent.ANY, (KeyEvent event) -> {
            if (event.getEventType().equals(KeyEvent.KEY_PRESSED)
                    && event.isAltDown()
                    && event.isControlDown()
                    && event.getCode() == KeyCode.M) {
                navigationActive = !navigationActive;
            }
            
            if (navigationActive
                    && event.getEventType().equals(KeyEvent.KEY_PRESSED)) {
                if (event.getCode() == KeyCode.LEFT) {
                    if (!presentation.showsIndex()) {
                        presentation.getActionController().prev();
                    }
                } else if (event.getCode() == KeyCode.RIGHT) {
                    if (presentation.showsIndex()) {
                        presentation.getSlideController().setIndex(0);
                    } else {
                        presentation.getActionController().next();
                    }
                } else if (event.getCode() == KeyCode.I) {
                    presentation.showIndex();
                } else if (event.getCode() == KeyCode.UP) {
                    if (!presentation.showsIndex()) {
                        presentation.getSlideController().prev();
                    }
                } else if (event.getCode() == KeyCode.DOWN) {
                    if (presentation.showsIndex()) {
                        presentation.getSlideController().setIndex(0);
                    } else {
                        presentation.getSlideController().next();
                    }
                }
            }
        });

        scene.setFill(new Color(122 / 255.0, 122 / 255.0, 122 / 255.0, 1));

        presentation.getView().prefWidthProperty().bind(scene.widthProperty());
        presentation.getView().prefHeightProperty().bind(scene.heightProperty());

        stage.setFullScreen(true);

        stage.setTitle("JavaOne 2013");
        stage.setScene(scene);
        stage.show();

    }
}
