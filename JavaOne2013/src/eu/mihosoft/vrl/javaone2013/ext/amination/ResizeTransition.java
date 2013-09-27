/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.ext.amination;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ResizeTransition extends eu.mihosoft.vrl.javaone2013.ext.animation.CachedTimelineTransition {

    /**
     * Create new FadeInTransition
     *
     * @param node The node to affect
     */
    public ResizeTransition(final Pane node, double preTime, double time, double width, double height) {
        super(
                node,
                TimelineBuilder.create()
                .keyFrames(
                new KeyFrame(Duration.millis(0), new KeyValue(node.prefWidthProperty(), node.getPrefWidth(), WEB_EASE)),
                new KeyFrame(Duration.millis(time), new KeyValue(node.opacityProperty(), width, WEB_EASE)),
                new KeyFrame(Duration.millis(0), new KeyValue(node.prefHeightProperty(), node.getPrefHeight(), WEB_EASE)),
                new KeyFrame(Duration.millis(time), new KeyValue(node.prefHeightProperty(), height, WEB_EASE)))
                .build());
        setCycleDuration(Duration.millis(time));
        setDelay(Duration.millis(preTime));
    }
}