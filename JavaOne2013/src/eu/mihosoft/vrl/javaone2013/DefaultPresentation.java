/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import eu.mihosoft.vrl.javaone2013.ext.animation.FadeInTransition;
import eu.mihosoft.vrl.javaone2013.ext.animation.FadeOutTransition;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.util.Duration;


/**
 * Default presentation implementation.
 * 
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public final class DefaultPresentation implements Presentation {

    private final StringProperty titleProperty = new SimpleStringProperty("Title");
    private final WritableSlideController slideController = new DefaultSlideController();
    private final ActionController slideActionCotroller = new SlideActionController(slideController);
    private final ObjectProperty<Pane> viewProperty = new SimpleObjectProperty();
    private final StackPane slideContainer = new StackPane();
    private Slide prevSlide;
    private WritableImage image;
    private SnapshotParameters sp;
    private final PresentationControlController controller;
    private final double scale = 0.25;
    private IndexSlide indexSlide;
    private final List<Transition> slideTransitions = new ArrayList<>();

    public DefaultPresentation() {

        setIndexSlide(new DefaultIndexSlide(this));

        final AnchorPane view = new AnchorPane();
        
        view.getStyleClass().addAll("index-slide-background");

        final AnchorPane outerContainer = new AnchorPane(view);

        AnchorPane.setTopAnchor(view, 0.0);
        AnchorPane.setBottomAnchor(view, 0.0);
        AnchorPane.setLeftAnchor(view, 0.0);
        AnchorPane.setRightAnchor(view, 0.0);

        final Spot spot = new Spot();
        spot.setVisible(false);

        spot.prefWidthProperty().bind(outerContainer.widthProperty());
        spot.prefHeightProperty().bind(outerContainer.heightProperty());

        outerContainer.getChildren().add(spot);

        outerContainer.setMouseTransparent(false);

        outerContainer.getStylesheets().add("/eu/mihosoft/vrl/javaone2013/resources/slide-style-light.css");

        AnchorPane.setTopAnchor(slideContainer, 0.0);
        AnchorPane.setBottomAnchor(slideContainer, 0.0);
        AnchorPane.setLeftAnchor(slideContainer, 0.0);
        AnchorPane.setRightAnchor(slideContainer, 0.0);

        viewProperty.set(outerContainer);

        view.getChildren().add(slideContainer);


        slideController.setOnShowSlide(new ShowSlideRequest() {
            @Override
            public void fulfil(Slide s) {
                addSlideToView();
            }
        });

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("PresentationControl.fxml"));

        try {
            fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(eu.mihosoft.vrl.workflow.demo.Main.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        controller = fxmlLoader.getController();
        controller.setPresentation(this);

        Pane presentationControlView = (Pane) fxmlLoader.getRoot();

        BoxBlur effect = new BoxBlur();
        effect.setWidth(15);
        effect.setHeight(15);
        effect.setIterations(3);

        controller.blurView.setEffect(effect);

        view.getChildren().add(presentationControlView);
        AnchorPane.setBottomAnchor(presentationControlView, -10.0);
        AnchorPane.setLeftAnchor(presentationControlView, 0.0);
        AnchorPane.setRightAnchor(presentationControlView, 0.0);

        initBlurImg(presentationControlView);

        showIndex();

        


//        slideContainer.parentProperty().addListener(new ChangeListener<Parent>() {
//            @Override
//            public void changed(ObservableValue<? extends Parent> observable, Parent oldValue, Parent newValue) {
//                if (oldValue != null) {
//                    oldValue.getScene().removeEventFilter(KeyEvent.KEY_PRESSED,
//                            CTRL_ALT_M_Listener);
//                }
//                if (newValue != null) {
//                    newValue.getScene().addEventFilter(KeyEvent.KEY_PRESSED,
//                            CTRL_ALT_M_Listener);
//                }
//            }
//        });
    }

    private void createFullSnaphot(final Pane presentationControlView) {

        if (getView().getWidth() <= 0
                || getView().getHeight() <= 0
                || presentationControlView.getOpacity() == 0) {
            return;
        }

        if (presentationControlView.getWidth() <= 0
                || presentationControlView.getHeight() <= 0) {
            return;
        }
        
        image = new WritableImage(
                (int) (presentationControlView.getWidth() * scale),
                (int) (presentationControlView.getHeight() * scale));

        sp = new SnapshotParameters();
        Rectangle2D rect = new Rectangle2D(
                presentationControlView.getLayoutX() * scale,
                presentationControlView.getLayoutY() * scale,
                presentationControlView.getWidth() * scale,
                presentationControlView.getHeight() * scale);
        sp.setViewport(rect);
        sp.setTransform(new Scale(scale, scale, 1));

        image = getSlideContainer().snapshot(sp, image);
        controller.blurView.setImage(image);
    }

    private void initBlurImg(final Pane presentationControlView) {

        final Duration oneFrameAmt = Duration.millis(1000 / 60);
        final KeyFrame oneFrame = new KeyFrame(oneFrameAmt,
                new EventHandler() {
            @Override
            public void handle(Event t) {
                createFullSnaphot(presentationControlView);
            }
        }); // oneFrame

        Timeline timeline = new Timeline();

        timeline.getKeyFrames().add(oneFrame);
        timeline.setCycleCount(Timeline.INDEFINITE);

        timeline.play();
    }

    private void addSlideToView() {

        removePrevSlide();
        addNextSlide();
    }

    private void addNextSlide() {

        final Slide currentNextSlide = slideController.getCurrent();
        currentNextSlide.setViewMode(ViewMode.FULL);

        if (getSlideContainer().getChildren().contains(currentNextSlide.getView())) {
            return;
        }

        double direction = 1;

        if (getSlideController().getDirection() < 0) {
            direction = -1;
        }

        currentNextSlide.getView().setOpacity(0);
//        currentNextSlide.getView().setManaged(true);
        getSlideContainer().getChildren().add(currentNextSlide.getView());
        slideController.getCurrent().getView().requestFocus();
        prevSlide = currentNextSlide;

        final FadeInTransition transition =
                new FadeInTransition(currentNextSlide.getView(), 0, 800);

        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                slideTransitions.remove(transition);
            }
        });

        slideTransitions.add(transition);

        transition.play();
    }

    private void addIndexSlide() {

        for (Transition transition : slideTransitions) {
            transition.stop();
        }

        slideTransitions.clear();

        Slide currentNextSlide = indexSlide;

        if (getSlideContainer().getChildren().contains(currentNextSlide.getView())) {
            return;
        }
//        currentNextSlide.getView().setManaged(true);
        getSlideContainer().getChildren().add(currentNextSlide.getView());
        prevSlide = currentNextSlide;
    }

    private void removePrevSlide() {

        if (prevSlide == null) {
            return;
        }

        final Slide currentPrevSlide = prevSlide;

        if (prevSlide == indexSlide) {
//            prevSlide.getView().setManaged(false);
            getSlideContainer().getChildren().remove(prevSlide.getView());
        }

        double direction = 1;

        if (getSlideController().getDirection() < 0) {
            direction = -1;
        }
        
        currentPrevSlide.getView().setOpacity(1.0);

        final FadeOutTransition transition = new FadeOutTransition(
                currentPrevSlide.getView(), 0, 800);

        transition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("done");
                getSlideContainer().getChildren().remove(currentPrevSlide.getView());
                currentPrevSlide.getView().setOpacity(1);

                slideTransitions.remove(transition);
            }
        });

        slideTransitions.add(transition);

        transition.play();
    }

    @Override
    public void addSlide(Slide s) {
        slideController.add(s);
        s.setPresentation(this);
        indexSlide.update();
    }

    @Override
    public SlideController getSlideController() {
        return slideController;
    }

    @Override
    public ActionController getActionController() {
        return slideActionCotroller;
    }

    @Override
    public void showIndex() {

        controller.root.setOpacity(0.0);

        getSlideController().reset();

        if (prevSlide == null) {

            indexSlide.update();
            addIndexSlide();
        } else {

            getSlideContainer().getChildren().remove(prevSlide.getView());

            indexSlide.update();
            addIndexSlide();
        }

    }

    private ReadOnlyObjectProperty<Pane> viewProperty() {
        return viewProperty;
    }
//
//    @Override
//    public void setView(Pane p) {
//        viewProperty().set(p);
//    }

    @Override
    public Pane getView() {
        return viewProperty().get();
    }

    @Override
    public void setIndexSlide(IndexSlide s) {
        this.indexSlide = s;
    }

    @Override
    public boolean showsIndex() {
        return getSlideContainer().getChildren().contains(indexSlide.getView());
    }

    /**
     * @return the slideContainer
     */
    StackPane getSlideContainer() {
        return slideContainer;
    }
}
class SlideActionController implements ActionController {

    private SlideController slideController;

    public SlideActionController(SlideController slideController) {
        this.slideController = slideController;
    }

    @Override
    public Action next() {

        ActionController ctrl = slideController.getCurrent().getActionController();

        if (ctrl == null) {
            return null;
        }

        if (!ctrl.hasNext()) {
            if (slideController.hasNext()) {
                ctrl = slideController.next().getActionController();
                ctrl.reset(); // ensure we are at first action
            }
        }

        return ctrl.next();
    }

    @Override
    public Action prev() {
        ActionController ctrl = slideController.getCurrent().getActionController();

        if (ctrl == null) {
            return null;
        }

        if (!ctrl.hasPrev()) {
            if (slideController.hasPrev()) {
                ctrl = slideController.prev().getActionController();
                ctrl.setToEnd(); // ensure we are at last action
            }
        }

        return ctrl.prev();
    }

    @Override
    public void reset() {

        slideController.reset();

        ActionController ctrl = slideController.getCurrent().getActionController();

        ctrl.reset();
    }

    @Override
    public void setIndex(int i) {
        throw new UnsupportedOperationException("Please use SlideController to specify slide position");
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("Please use SlideController to get number of slides");
    }

    @Override
    public int getIndex() {
        throw new UnsupportedOperationException("Please use SlideController to get the index");
    }

    @Override
    public boolean hasNext() {
        ActionController ctrl = slideController.getCurrent().getActionController();

        if (ctrl == null) {
            return false;
        }

        if (!ctrl.hasNext()) {
            if (slideController.hasNext()) {
                ctrl = slideController.next().getActionController();
                ctrl.reset(); // ensure we are at first action
                return ctrl.hasNext();
            }
        } else {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPrev() {
        ActionController ctrl = slideController.getCurrent().getActionController();

        if (ctrl == null) {
            return false;
        }

        if (!ctrl.hasPrev()) {
            if (slideController.hasPrev()) {
                ctrl = slideController.prev().getActionController();
                ctrl.setToEnd();// ensure we are at last action
                return ctrl.hasPrev();
            }
        } else {
            return true;
        }

        return false;
    }

    @Override
    public Action getCurrent() {
        return slideController.getCurrent().getActionController().getCurrent();
    }

    @Override
    public void setToEnd() {
        slideController.setToEnd();
        slideController.getCurrent().getActionController().setToEnd();
    }

    @Override
    public void addIndexChangeListener(ChangeListener<? super Number> listener) {
        throw new UnsupportedOperationException("Please use SlideController to register slide change listeners");
    }

    @Override
    public void removeIndexChangeListener(ChangeListener<? super Number> listener) {
        throw new UnsupportedOperationException("Please use SlideController to remove slide change listeners");
    }

    @Override
    public Action get(int i) {
        throw new UnsupportedOperationException("Please use SlideController to get slides by index");
    }

    @Override
    public void addIndexInvalidationListener(InvalidationListener listener) {
        throw new UnsupportedOperationException("Please use SlideController to register invalidation listeners"); // TODO NB-AUTOGEN
    }

    @Override
    public void removeIndexInvalidationListener(ChangeListener<? super Number> listener) {
        throw new UnsupportedOperationException("Please use SlideController to remove invalidation listeners"); // TODO NB-AUTOGEN
    }

    @Override
    public int getDirection() {
        throw new UnsupportedOperationException("Not supported yet."); // TODO NB-AUTOGEN
    }
}
