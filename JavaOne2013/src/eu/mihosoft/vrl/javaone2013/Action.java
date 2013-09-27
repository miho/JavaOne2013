/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 * Undoable slide action.
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface Action {

    /**
     * Performs the action.
     */
    public void perform();

    /**
     * Undoes the action.
     */
    public void undo();
    
}
