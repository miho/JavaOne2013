/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.ext.amination;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class MoveToTransition extends eu.mihosoft.vrl.javaone2013.ext.animation.CachedTimelineTransition {

    private double preTime;
    private double time;
    private double toX;
    private double toY;
    private double toZ;
    private double fromX;
    private double fromY;
    private double fromZ;

    public MoveToTransition(Node node, double preTime, double time, double toX, double toY, double toZ) {
        super(node, null);
        setCycleDuration(Duration.millis(time));
        setDelay(Duration.millis(preTime));
        this.preTime = preTime;
        this.time = time;
        this.toX = toX;
        this.toY = toY;
        this.toZ = toZ;
    }

    @Override
    protected void starting() {
        double startY = -node.localToScene(0, 0).getY() - node.getBoundsInParent().getHeight();
        timeline = TimelineBuilder.create()
                .keyFrames(
                new KeyFrame(Duration.millis(0),
                new KeyValue(node.translateXProperty(), node.getTranslateX(), WEB_EASE),
                new KeyValue(node.translateYProperty(), node.getTranslateY(), WEB_EASE),
                new KeyValue(node.translateZProperty(), node.getTranslateZ(), WEB_EASE)),
                new KeyFrame(Duration.millis(time),
                new KeyValue(node.translateXProperty(), toX, WEB_EASE),
                new KeyValue(node.translateYProperty(), toY, WEB_EASE),
                new KeyValue(node.translateZProperty(), toZ, WEB_EASE)))
                .build();
        super.starting();
    }
}
