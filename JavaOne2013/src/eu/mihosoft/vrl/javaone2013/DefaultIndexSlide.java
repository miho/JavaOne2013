/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 * Default index slide implementation.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public final class DefaultIndexSlide extends TemplateSlide implements IndexSlide {

    /**
     * content grid pane.
     */
    private final GridPane content = new GridPane();

    /**
     * Constructor.
     * 
     * @param presentation presentation this index slide belongs to
     */
    public DefaultIndexSlide(Presentation presentation) {

        setTitle("");
        
        setPresentation(presentation);

        initView();
    }

    /**
     * Initializes the view of this index slide.
     */
    private void initView() {

        // setup content pane
        content.getChildren().clear();
//        content.setStyle("-fx-border-color: red;");
        content.setPadding(new Insets(10));
        content.setHgap(30);
        content.setVgap(30);
//        content.setPrefColumns(5);
//        content.setPrefTileWidth(160);
//        content.setPrefTileHeight(120);
        content.setAlignment(Pos.CENTER);
//        content.setTileAlignment(Pos.CENTER);

        // iterate over slides, create and add their container and add
        // the container to the content pane
        // grid size 8x6
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                int index = i * 8 + j;

                Slide s = null;

                if (index < getPresentation().getSlideController().size()) {
                    s = getPresentation().getSlideController().get(index);
                    content.add(createSlideContainer(s, index), j, i);
                }
            }
        }

        // clear the toplevel content pane
        getContent().getChildren().clear();
        //add the grid pane to the toplevel content pane
        getContent().getChildren().add(content);

        // define the style class for this slide
        // TODO there are better ways to define default class
        setStyleClass("index-slide-background");
    }

    /**
     * Creates and returns a container for the specified slide.
     * @param s slide
     * @param index slide index
     * @return the requested slide container
     */
    private Parent createSlideContainer(final Slide s, final int index) {

        if (s == null) {
            return null;
        }

        final ScalableContentPane result = new ScalableContentPane();
        result.getStyleClass().setAll("slide-index-background");

        AnchorPane slideContainer = new AnchorPane();
        result.setContentPane(slideContainer);


        s.getView().setOpacity(1.0);
        result.getContentPane().getChildren().add(s.getView());
        
        s.setViewMode(ViewMode.INDEX);
        
        AnchorPane.setTopAnchor(s.getView(), 0.0);
        AnchorPane.setBottomAnchor(s.getView(), 0.0);
        AnchorPane.setLeftAnchor(s.getView(), 0.0);
        AnchorPane.setRightAnchor(s.getView(), 0.0);


        final Pane frontPane = new Pane();
        frontPane.getStyleClass().setAll("slide-index-foreground");
        slideContainer.getChildren().add(frontPane);
        
        AnchorPane.setTopAnchor(frontPane, 0.0);
        AnchorPane.setBottomAnchor(frontPane, 0.0);
        AnchorPane.setLeftAnchor(frontPane, 0.0);
        AnchorPane.setRightAnchor(frontPane, 0.0);


        frontPane.onMouseClickedProperty().set(
                (EventHandler<MouseEvent>) (MouseEvent event) -> {
            getPresentation().getSlideController().setIndex(index);
            s.setViewMode(ViewMode.FULL);
        });


        result.setOnMouseEntered((MouseEvent mouseEvent) -> {
            result.toFront();
            
            ScaleTransition scaleTransition = 
                    new ScaleTransition(Duration.millis(80), result);
            scaleTransition.setToX(1.1);
            scaleTransition.setToY(1.1);
            scaleTransition.setToZ(1.1);
            
            scaleTransition.play();
            
            scaleTransition.setOnFinished((ActionEvent event) -> {
                result.setEffect(new DropShadow(15, Color.BLACK));
            });
        });

        frontPane.setOnMouseExited((MouseEvent mouseEvent) -> {
            result.setEffect(null);
            
            ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(80), result);
            scaleTransition.setToX(1.0);
            scaleTransition.setToY(1.0);
            scaleTransition.setToZ(1.0);
            
            scaleTransition.setOnFinished((ActionEvent event) -> {
                result.setEffect(null);
            });

            scaleTransition.play();
        });

        result.getContentPane().setPrefSize(1280, 1024);
        result.getContentPane().setMinSize(1280, 1024);
        result.getContentPane().setMaxSize(1280, 1024);
        result.setPrefSize(320, 240);
        result.setMinSize(320, 240);
        result.setMaxSize(320, 240);
        result.setAspectScale(true);
        result.requestLayout();

        return result;
    }

    @Override
    public void update() {
        initView();
    }

    @Override
    public void showSlide(int index) {
        //throw new UnsupportedOperationException("Not supported yet."); // TODO NB-AUTOGEN
    }
}
