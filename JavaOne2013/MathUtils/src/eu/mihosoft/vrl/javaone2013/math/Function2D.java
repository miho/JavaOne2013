/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

/**
 * Simple 2D function interface.
 * 
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public interface Function2D {
    
    /**
     * Evaluates this function at the given point (x,y).
     * @param x x value
     * @param y y value
     * @return the function value at (x,y)
     */
    public double run(double x, double y);
}
