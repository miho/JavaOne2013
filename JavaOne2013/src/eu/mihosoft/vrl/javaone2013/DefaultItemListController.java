/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javafx.beans.InvalidationListener;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;

/**
 * Default implementation of an item list controller.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DefaultItemListController<T> implements WritableItemListController<T> {

    protected IntegerProperty indexProperty = new SimpleIntegerProperty();
    protected List<T> items = new ArrayList<>();
    private int direction = 1;

    public DefaultItemListController() {
    }

    @Override
    public void add(T... actions) {
        this.items.addAll(Arrays.asList(actions));
    }

    @Override
    public void remove(T... actions) {
        this.items.removeAll(Arrays.asList(actions));
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public T next() {
        direction = 0;

        int minIndex = 0;
        int maxIndex = items.size() - 1;

        if (getIndex() >= maxIndex) {
            return null;
        }

        indexProperty.set(indexProperty.get() + 1);

        direction = 1;

        return items.get(getIndex());

//        actions.get(index).perform();
    }

    @Override
    public T prev() {
        direction = 0;
        int minIndex = 0;
        int maxIndex = items.size() - 1;

        if (getIndex() <= minIndex) {
            return null;
        }

        if (getIndex() <= maxIndex) {
//            actions.get(index).undo();
        }

        direction = -1;

        indexProperty.set(indexProperty.get() - 1);

        return items.get(getIndex());
    }

    @Override
    public void reset() {

        setIndex(0);

        indexProperty.set(-1);

//        if (!items.isEmpty()) items.get(0).
//
////        for (Action action : actions) {
////            action.undo();
////        }
    }

    @Override
    public void setToEnd() {
        if (size() > 0) {
            setIndex(size() - 1);
            indexProperty.set(size());
        }
    }

    @Override
    public void setIndex(int newIndex) {

        int minIndex = 0;
        int maxIndex = items.size() - 1;

        int prevIndex = indexProperty.get();

        if (prevIndex < newIndex) {
            direction = 1;
        } else if (prevIndex > newIndex) {
            direction = -1;
        } else {
            direction = 0;
        }

        indexProperty.set(newIndex);

//        int di = index - prevIndex;
//
//        for (int i = prevIndex; i > index && i >= minIndex && i <= maxIndex; i += di) {
//            if (di < 0) {
//                actions.get(i).undo();
//            } else {
//                actions.get(i).perform();
//            }
//        }
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public int getIndex() {
        return indexProperty.get();
    }

    @Override
    public boolean hasNext() {
        int minIndex = 0;
        int maxIndex = items.size() - 1;
        return getIndex() < maxIndex;
    }

    @Override
    public boolean hasPrev() {
        int minIndex = 0;
        int maxIndex = items.size() - 1;
        return getIndex() > minIndex;
    }

    @Override
    public T getCurrent() {

//        int minIndex = 0;
//        int maxIndex = items.size() - 1;
//
//        if (getIndex() < minIndex || getIndex() > maxIndex) {
//            return null;
//        }

        T result = items.get(getIndex());

        return result;
    }

    @Override
    public void addIndexChangeListener(ChangeListener<? super Number> listener) {
        indexProperty.addListener(listener);
    }

    @Override
    public void removeIndexChangeListener(ChangeListener<? super Number> listener) {
        indexProperty.removeListener(listener);
    }

    @Override
    public void addIndexInvalidationListener(InvalidationListener listener) {
        indexProperty.addListener(listener);
    }

    @Override
    public void removeIndexInvalidationListener(ChangeListener<? super Number> listener) {
        indexProperty.removeListener(listener);
    }

    @Override
    public T get(int i) {
        return items.get(i);
    }

    /**
     * @return the direction
     */
    public int getDirection() {
        return direction;
    }
}
