/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class FunctionInput {
    private String function;
    private int resolution;
    private DoubleProperty aProperty = new SimpleDoubleProperty();

    public FunctionInput() {
    }

    public FunctionInput(String function, int resolution, double a) {
        this.function = function;
        this.resolution = resolution;
    }
    
    

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the resolution
     */
    public int getResolution() {
        return resolution;
    }

    /**
     * @param resolution the resolution to set
     */
    public void setResolution(int resolution) {
        this.resolution = resolution;
    }
    
    public DoubleProperty aProperty() {
        return aProperty;
    }
    
    public void setA(double a) {
        aProperty().set(a);
    }
    
    public double getA() {
        return aProperty.get();
    }
}
