/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

/**
 * Simple 1D function interface.
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public interface Function1D {

    /**
     * Evaluates this function at x.
     *
     * @param x x value
     * 
     * @return the function value at x
     */
    public double run(double x);
}
