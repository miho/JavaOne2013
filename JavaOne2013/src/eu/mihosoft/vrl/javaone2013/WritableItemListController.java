/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface WritableItemListController<T> extends ItemListController<T> {

    void add(T... actions);

//    void add(int i, T... actions);
    void clear();

    void remove(T... actions);
}
