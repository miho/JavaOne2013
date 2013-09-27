/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

import static eu.mihosoft.vrl.javaone2013.math.VFX3DUtil.addMouseBehavior;
import java.util.List;
import java.util.stream.Collectors;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Rectangle;

/**
 * Container for a 3D geometry (triangle mesh).
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class GeometryContainer extends StackPane {

    /**
     * mesh view (geometry node)
     */
    private MeshView view;

    /**
     * group that contains the mesh view and optionally other nodes for
     * additional visualizations (e.g. ray picking circle)
     */
    private Group group;

    /**
     * Constructor.
     */
    public GeometryContainer() {
        init();
    }

    /**
     * Constructor.
     *
     * @param view mesh view (geometry node)
     */
    public GeometryContainer(MeshView view) {
        init();
        setView(view);

    }

    /**
     * initializes this geometry container.
     */
    private void init() {

        Rectangle clip = new Rectangle();
        clip.widthProperty().bind(widthProperty());
        clip.heightProperty().bind(heightProperty());
        setClip(clip);

        group = new Group();

        getChildren().add(getGroup());

        // add rotation behavior
        addMouseBehavior(getGroup(), getGroup(), MouseButton.PRIMARY);

        setMinSize(0, 0);
    }

    /**
     * @return the view
     */
    public MeshView getView() {
        return view;
    }

    /**
     * @param view the view to set
     */
    public final void setView(MeshView view) {
        this.view = view;

        // remove all previously added mesh views
        List<Node> nodesToRemove
                = getGroup().getChildren().stream().filter(
                        e -> e instanceof MeshView).
                collect(Collectors.toList());

        getGroup().getChildren().removeAll(nodesToRemove);
        
        // finally add the new view
        getGroup().getChildren().add(view);

    }

    /**
     * @return the group
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Clears this container (removes all children).
     */
    public void clear() {
        getChildren().clear();

        group = new Group();
        getChildren().add(getGroup());

        // add rotation behavior
        addMouseBehavior(getGroup(), getGroup(), MouseButton.PRIMARY);
    }

}
