/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Spot extends Pane {

//    private final BooleanProperty activeProperty = new SimpleBooleanProperty();
    private EventHandler<MouseEvent> mouseListener;
    private EventHandler<KeyEvent> keyListener;
    private final BooleanProperty enabledProperty = new SimpleBooleanProperty(true);

    public Spot() {

        setVisible(false);

        final Circle c = new Circle(120);
        final Rectangle rect = new Rectangle();
        rect.setFill(new Color(0, 0, 0, 0.5));

        Group g = new Group(rect, c);

        getChildren().add(g);

        g.setBlendMode(BlendMode.MULTIPLY);

        rect.setX(0);
        rect.setY(0);

        rect.widthProperty().bind(widthProperty());
        rect.heightProperty().bind(heightProperty());

        c.setFill(new Color(1, 1, 1, 1));

        setMouseTransparent(true);

        mouseListener = (MouseEvent event) -> {
            c.setCenterX(event.getX());
            c.setCenterY(event.getY());
        };

        keyListener = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {

                if (event.getEventType().equals(KeyEvent.KEY_PRESSED)
                        && event.isAltDown()
                        && event.isControlDown()
                        && event.getCode() == KeyCode.M) {
                    Spot.this.setEnabled(!Spot.this.isEnabled());
                }

                if (event.isShiftDown() && isEnabled()) {
                    setVisible(true);
                    setManaged(true);
                    requestLayout();
                } else {
                    setVisible(false);
                    setManaged(false);
                }
            }
        };


        sceneProperty().addListener(new ChangeListener<Scene>() {
            @Override
            public void changed(ObservableValue<? extends Scene> observable, Scene oldValue, Scene newValue) {
                if (oldValue != null) {
                    oldValue.removeEventFilter(MouseEvent.ANY, mouseListener);
                    oldValue.removeEventFilter(KeyEvent.ANY, keyListener);
                }
                if (newValue != null) {
                    newValue.addEventFilter(MouseEvent.ANY, mouseListener);
                    newValue.addEventFilter(KeyEvent.ANY, keyListener);
                }
            }
        });
    }

    private void setEnabled(boolean b) {
        enabledProperty.set(b);
    }

    public boolean isEnabled() {
        return enabledProperty().get();
    }

    public ReadOnlyBooleanProperty enabledProperty() {
        return enabledProperty;
    }
}
