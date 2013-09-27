/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;

/**
 * Itemlist controller.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public interface ItemListController<T> {

    public T next();

    public T prev();

    public void reset();
    
    public void setToEnd();

    public void setIndex(int i);

    public int size();

    public int getIndex();

    public boolean hasNext();

    boolean hasPrev();
    
    public T getCurrent();
    
    public T get(int i);
    
    public void addIndexChangeListener(ChangeListener<? super Number> listener);
    public void removeIndexChangeListener(ChangeListener<? super Number> listener);

    void addIndexInvalidationListener(InvalidationListener listener);
    void removeIndexInvalidationListener(ChangeListener<? super Number> listener);

    /**
     * @return the direction
     */
    int getDirection();
}