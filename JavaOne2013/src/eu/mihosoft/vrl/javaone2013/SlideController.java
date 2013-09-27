/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface SlideController extends ItemListController<Slide>{
    public void setOnShowSlide(ShowSlideRequest req);
}
