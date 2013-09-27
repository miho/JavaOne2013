/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 * Default slide controller implementation.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
class DefaultSlideController extends DefaultItemListController<Slide> implements WritableSlideController {

    private ShowSlideRequest req;

    @Override
    public void setOnShowSlide(ShowSlideRequest req) {
        this.req = req;
    }

    @Override
    public void setIndex(int i) {

        super.setIndex(i);

        if (req != null && size() > 0) {
            req.fulfil(getCurrent());
        }
    }

    @Override
    public Slide next() {
        Slide result = super.next();
        if (req != null && result != null) {
            req.fulfil(result);
        }
        return result;
    }

    @Override
    public Slide prev() {
        Slide result = super.prev();
        if (req != null && result != null) {
            req.fulfil(result);
        }
        return result;
    }
}
