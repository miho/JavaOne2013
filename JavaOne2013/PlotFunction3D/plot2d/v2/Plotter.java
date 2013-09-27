/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.plot2d.v2;

import eu.mihosoft.vrl.javaone2013.math.GroovyFunction1D;
import eu.mihosoft.vrl.javaone2013.math.MathUtil;
import eu.mihosoft.vrl.workflow.VNode;
import javafx.scene.Node;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Plotter {
    
    private VNode node;
    
    public Node plot(FunctionInput functionInput) {
        //
        GroovyFunction1D function =
                new GroovyFunction1D(functionInput.getFunction());


        Node chart = MathUtil.createChart("f(x)="+functionInput.getFunction(),
                function, 0, 10, functionInput.getResolution());
        return chart;
    }

    /**
     * @return the node
     */
    public VNode getNode() {
        return node;
    }

    /**
     * @param node the node to set
     */
    public void setNode(VNode node) {
        this.node = node;
    }
}
