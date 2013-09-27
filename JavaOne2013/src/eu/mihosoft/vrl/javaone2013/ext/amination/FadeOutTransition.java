package eu.mihosoft.vrl.javaone2013.ext.animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.TimelineBuilder;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animate a fade out effect on a node
 * 
 * Port of FadeOut from Animate.css http://daneden.me/animate by Dan Eden
 * 
 * {@literal @}keyframes fadeOut {
 * 	0% {opacity: 1;}	
 * 	100% {opacity: 0;}
 * }
 * 
 * @author Jasper Potts
 */
public class FadeOutTransition extends CachedTimelineTransition {
    /**
     * Create new FadeOutTransition
     * 
     * @param node The node to affect
     */
    public FadeOutTransition(final Node node, double preTime, double time) {
        super(
            node,
            TimelineBuilder.create()
                .keyFrames(
                    new KeyFrame(Duration.millis(0),    new KeyValue(node.opacityProperty(), node.getOpacity(), WEB_EASE)),
                    new KeyFrame(Duration.millis(time),  new KeyValue(node.opacityProperty(), 0, WEB_EASE))
                )
                .build()
            );
        setCycleDuration(Duration.millis(time));
        setDelay(Duration.millis(preTime));
    }
}
