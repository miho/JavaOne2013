/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

/**
 * 1D sample function.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class SineFunction1D implements Function1D{

    @Override
    public double run(double x) {
        return Math.sin(x*x*0.3);
    }
    
}
