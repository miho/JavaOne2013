/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 * Index slide.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface IndexSlide extends Slide{
    
    /**
     * Updates the index.
     */
    public void update();

    /**
     * Shows slide i.
     * @param index index of the slide to show
     */
    public void showSlide(int index);
}
