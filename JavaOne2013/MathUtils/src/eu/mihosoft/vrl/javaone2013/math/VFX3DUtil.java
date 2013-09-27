/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package eu.mihosoft.vrl.javaone2013.math;

import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.shape.MeshView;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.CullFace;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.transform.Rotate;

/**
 * Utility class that allows to visualize meshes created with null {@link MathUtil#evaluateFunction(
 *   eu.mihosoft.vrl.javaone2013.math.Function2D,
 *   int, int, float, float, float, double, double, double, double)
 * }.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VFX3DUtil {

    private VFX3DUtil() {
        throw new AssertionError("don't instanciate me!");
    }

    /**
     * Adds the specified mesh to a group and returns it.
     *
     * @param prev previous geometry container (may be null)
     * @param mesh mesh
     *
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToParent(GeometryContainer prev,
            TriangleMesh mesh) {
        return meshToParent(prev, mesh, Color.ORANGE, Color.ORANGE);
    }

    /**
     * Adds the specified mesh to a group and returns it. In addition this
     * method allows the definition of a mouse event handler.
     *
     * @param prev previous container (will be reused if possible)
     * @param mesh mesh
     * @param type eventhandler type (may be <code>null</code>)
     * @param evtHandler eventhandler (may be <code>null</code>)
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToParent(GeometryContainer prev,
            TriangleMesh mesh,
            EventType<MouseEvent> type, EventHandler<MouseEvent> evtHandler) {
        return meshToParent(prev, mesh, Color.ORANGE, Color.ORANGE, type, evtHandler);
    }

    /**
     * Adds the specified mesh to a group and returns it. In addition this
     * method allows the definition of colors.
     *
     * @param prev previous container (will be reused if possible)
     * @param mesh mesh
     * @param diffuse diffuse color
     * @param specular specular color
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToParent(
            GeometryContainer prev, TriangleMesh mesh, Color diffuse,
            Color specular) {
        return meshToParent(prev, mesh, diffuse, specular, null, null);
    }

    /**
     * Adds the specified mesh to a group and returns it. In addition this
     * method allows the definition of a mouse event handler and colors.
     *
     * @param prev previous container (will be reused if possible)
     * @param mesh mesh
     * @param diffuse diffuse color
     * @param specular specular color
     * @param type eventhandler type (may be <code>null</code>)
     * @param evtHandler eventhandler (may be <code>null</code>)
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToParent(GeometryContainer prev,
            TriangleMesh mesh, Color diffuse, Color specular,
            EventType<MouseEvent> type, EventHandler<MouseEvent> evtHandler) {

        // convert mesh to node (via meshview)
        final GeometryContainer meshNode = meshToNode(prev, mesh, diffuse, specular);

        // add the specified eventhandler (if any)
        if (type != null && evtHandler != null) {
            meshNode.getGroup().addEventHandler(type, evtHandler);
        }

        return meshNode;
    }

    /**
     *
     * @param prev previous container (will be reused if possible)
     * @param mesh mesh
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToNode(GeometryContainer prev,
            TriangleMesh mesh) {
        return meshToNode(prev, mesh, Color.ORANGE, Color.AQUA);
    }

    /**
     * Converts the specified mesh to a MeshView node that can be added to the
     * scene graph.
     *
     * @param prev previous container (will be reused if possible)
     * @param mesh mesh
     * @param diffuse diffuse color
     * @param specular specular color
     * @return container that contains the visualization of the specified mesh
     */
    public static GeometryContainer meshToNode(GeometryContainer prev,
            TriangleMesh mesh, Color diffuse, Color specular) {

        // material that shall be used to visualize the mesh
        PhongMaterial material = new PhongMaterial();
        
        // setup material
        material.setDiffuseColor(diffuse);
        material.setSpecularColor(specular);
        MeshView meshView;

        // reuse mesh view from prev container if avalable
        if (prev != null && prev.getView() != null) {
            meshView = prev.getView();
            meshView.setMesh(mesh);
        } else {
            meshView = new MeshView(mesh);
        }
        
        // setup material & draw mode
        meshView.setMaterial(material);
        meshView.setDrawMode(DrawMode.FILL); // fill the mesh (wire also possible)
        meshView.setCullFace(CullFace.NONE); // show both sides of the mesh

        GeometryContainer result;

        // reuse previous container if available
        if (prev != null) {
            result = prev;
            result.setView(meshView);
        } else {
            result = new GeometryContainer(meshView);
        }

        return result;
    }

    /**
     * Adds rotation behavior to the specified node.
     *
     * @param n node
     * @param eventReceiver receiver of the event
     * @param btn mouse button that shall be used for this behavior
     */
    public static void addMouseBehavior(
            Node n, Scene eventReceiver, MouseButton btn) {
        eventReceiver.addEventHandler(MouseEvent.ANY,
                new MouseBehaviorImpl1(n, btn));
    }

    /**
     * Adds rotation behavior to the specified node.
     *
     * @param n node
     * @param eventReceiver receiver of the event
     * @param btn mouse button that shall be used for this behavior
     */
    public static void addMouseBehavior(
            Node n, Node eventReceiver, MouseButton btn) {
        eventReceiver.addEventHandler(MouseEvent.ANY,
                new MouseBehaviorImpl1(n, btn));
    }
}

// rotation behavior implementation
class MouseBehaviorImpl1 implements EventHandler<MouseEvent> {

    private double anchorAngleX;
    private double anchorAngleY;
    private double anchorX;
    private double anchorY;
    private final Rotate rotateX = new Rotate(0, 0, 0, 0, Rotate.X_AXIS);
    private final Rotate rotateZ = new Rotate(0, 0, 0, 0, Rotate.Z_AXIS);
    private MouseButton btn;

    public MouseBehaviorImpl1(Node n, MouseButton btn) {
        n.getTransforms().addAll(rotateX, rotateZ);
        this.btn = btn;

        if (btn == null) {
            this.btn = MouseButton.MIDDLE;
        }
    }

    @Override
    public void handle(MouseEvent t) {
        if (!btn.equals(t.getButton())) {
            return;
        }

        t.consume();

        if (MouseEvent.MOUSE_PRESSED.equals(t.getEventType())) {
            anchorX = t.getSceneX();
            anchorY = t.getSceneY();
            anchorAngleX = rotateX.getAngle();
            anchorAngleY = rotateZ.getAngle();
            t.consume();
        } else if (MouseEvent.MOUSE_DRAGGED.equals(t.getEventType())) {
            rotateZ.setAngle(anchorAngleY + (anchorX - t.getSceneX()) * 0.7);
            rotateX.setAngle(anchorAngleX - (anchorY - t.getSceneY()) * 0.7);

        }

    }
}
