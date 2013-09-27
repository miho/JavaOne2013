/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 * Default implementation for action controller interface.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DefaultActionController extends DefaultItemListController<Action> implements WritableActionController {

    @Override
    public Action next() {

        int minIndex = 0;
        int maxIndex = items.size() - 1;

        if (indexProperty.get() >= maxIndex) {
            return null;
        }

        indexProperty.set(indexProperty.get() + 1);

        items.get(indexProperty.get()).perform();

        return items.get(indexProperty.get());
    }

    @Override
    public Action prev() {
        int minIndex = 0;
        int maxIndex = items.size() - 1;

        if (indexProperty.get() <= minIndex) {
            return null;
        }

        if (indexProperty.get() <= maxIndex) {
            items.get(indexProperty.get()).undo();
        }

        indexProperty.set(indexProperty.get() - 1);

        return items.get(indexProperty.get());
    }

    @Override
    public void reset() {
        super.reset();
        
        for (Action action : items) {
            action.undo();
        }
    }

    @Override
    public void setIndex(int newIndex) {
        int minIndex = 0;
        int maxIndex = items.size() - 1;

        int prevIndex = indexProperty.get();

        indexProperty.set(newIndex);

        int di = indexProperty.get() - prevIndex;

        for (int i = prevIndex; i > indexProperty.get() && i >= minIndex && i <= maxIndex; i += di) {
            if (di < 0) {
                items.get(i).undo();
            } else {
                items.get(i).perform();
            }
        }
    }
}
