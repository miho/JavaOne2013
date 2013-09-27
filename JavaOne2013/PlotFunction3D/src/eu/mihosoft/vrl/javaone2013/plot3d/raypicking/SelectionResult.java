/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot3d.raypicking;

import javafx.geometry.Point3D;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class SelectionResult {
    private Point3D selectionPoint;
    private Double value;

    public SelectionResult(Point3D selectionPoint, Double value) {
        this.selectionPoint = selectionPoint;
        this.value = value;
    }

    public SelectionResult() {
    }
    
    

    /**
     * @return the selectionPoint
     */
    public Point3D getSelectionPoint() {
        return selectionPoint;
    }

    /**
     * @return the value
     */
    public Double getValue() {
        return value;
    }
}
