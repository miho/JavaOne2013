/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * Slide that displays an image.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class ImageSlide extends NodeSlide {

    private final StringProperty pathProperty = new SimpleStringProperty();
    private final ImageView view;
    private final Pane viewPane = new Pane();

    public ImageSlide(String image_path) {
        view = new ImageView(image_path);

        viewPane.getChildren().add(view);
        setView(viewPane);
        view.setPreserveRatio(true);
        view.setSmooth(true);
        view.setCache(true);
        view.fitWidthProperty().bind(viewPane.widthProperty());
        view.fitHeightProperty().bind(viewPane.heightProperty());
    }

    /**
     * @return the pathProperty
     */
    public StringProperty pathProperty() {
        return pathProperty;
    }

    public void setPath(String path) {
        pathProperty().set(path);
    }

    public String getPath() {
        return pathProperty().get();
    }

    @Override
    public void setViewMode(ViewMode mode) {
        if (mode == ViewMode.INDEX) {
            view.layoutXProperty().unbind();
            view.layoutYProperty().unbind();   
            view.setPreserveRatio(false);
            view.setLayoutX(0);
            view.setLayoutY(0);
        } else {
            view.setPreserveRatio(true);
            view.layoutXProperty().bind(new DoubleBinding() {
                {
                    super.bind(viewPane.widthProperty());
                }

                @Override
                protected double computeValue() {
                    return viewPane.getWidth() / 2 - view.getBoundsInLocal().getWidth() / 2;
                }
            });
            view.layoutYProperty().bind(new DoubleBinding() {
                {
                    super.bind(viewPane.heightProperty());
                }

                @Override
                protected double computeValue() {
                    return viewPane.getHeight() / 2 - view.getBoundsInLocal().getHeight() / 2;
                }
            });
        }
    }
}
