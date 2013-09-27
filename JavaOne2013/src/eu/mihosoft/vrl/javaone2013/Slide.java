/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.paint.Color;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Slide {

    ObjectProperty<Presentation> presentationProperty();

    public void setPresentation(Presentation p);

    public Presentation getPresentation();

    public void addActions(Action... action);

    public void removeActions(Action... action);

    public StringProperty titleProperty();

    public void setTitle(String title);

    public String getTitle();
    
    public void setTitleColor(Color c);

    public ObjectProperty<Node> viewProperty();

    public void setView(Node n);

    public Node getView();

    public ActionController getActionController();
    
    public void setStyleClass(String styleCls);
    
    public void setViewMode(ViewMode mode);
}
