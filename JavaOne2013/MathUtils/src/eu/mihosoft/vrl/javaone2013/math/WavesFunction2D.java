/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

/**
 * 2D sample function.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class WavesFunction2D implements Function2D{

    @Override
    public double run(double x, double y) {
        return Math.sin(Math.sqrt(x*x+y*y));
    }
    
}
