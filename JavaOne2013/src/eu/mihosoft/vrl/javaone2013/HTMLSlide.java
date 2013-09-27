/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.web.WebView;

/**
 * Slide that displays html content.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
final class HTMLSlide extends NodeSlide {

    private final WebView view = new WebView();
    private final StringProperty pathProperty = new SimpleStringProperty();

    public HTMLSlide(String htmlpath) {
        setView(view);
        view.getEngine().load(htmlpath);
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
}
