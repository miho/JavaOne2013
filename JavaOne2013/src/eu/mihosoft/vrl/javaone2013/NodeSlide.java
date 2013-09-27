/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

/**
 * Node slide.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class NodeSlide implements Slide {

    private final StringProperty titleProperty = new SimpleStringProperty("Title");
    private final ObjectProperty<Presentation> presentationProperty = new SimpleObjectProperty<>();
    private final ObjectProperty<Node> contentProperty = new SimpleObjectProperty<>();
    private final WritableActionController actionController = new DefaultActionController();
    private StackPane contentPane;

    /**
     * Constructor.
     */
    public NodeSlide() {

        contentPane = new StackPane();
        contentPane.setCache(true);
        contentPane.setCacheHint(CacheHint.SCALE);

        contentProperty.addListener((ObservableValue<? extends Node> ov, Node t, Node t1) -> {
            if (t != null) {
                contentPane.getChildren().remove(t);
            }
            if (t1 != null) {
                contentPane.getChildren().add(t1);
            }
        });

    }

    @Override
    public ObjectProperty<Presentation> presentationProperty() {
        return presentationProperty;
    }

    @Override
    public void setPresentation(Presentation p) {
        presentationProperty().set(p);
    }

    @Override
    public Presentation getPresentation() {
        return presentationProperty().get();
    }

    @Override
    public void addActions(Action... action) {
        actionController.add(action);
    }

    @Override
    public void removeActions(Action... action) {
        actionController.remove(action);
    }

    public int getNumberOfActions() {
        return actionController.size();
    }

    @Override
    public StringProperty titleProperty() {
        return titleProperty;
    }

    @Override
    public void setTitle(String title) {
        titleProperty().set(title);
    }

    @Override
    public String getTitle() {
        return titleProperty().get();
    }

    @Override
    public ObjectProperty<Node> viewProperty() {
        return contentProperty;
    }

    @Override
    public void setView(Node n) {
        viewProperty().set(n);
    }

    @Override
    public Node getView() {
        return viewProperty().get();
    }

    @Override
    public ActionController getActionController() {
        return actionController;
    }

    @Override
    public void setStyleClass(String styleCls) {
        getView().getStyleClass().setAll(styleCls);
    }

    @Override
    public void setTitleColor(Color c) {
        throw new UnsupportedOperationException("Not supported yet."); // TODO NB-AUTOGEN
    }

    @Override
    public void setViewMode(ViewMode mode) {
        //
    }
    
    
}
