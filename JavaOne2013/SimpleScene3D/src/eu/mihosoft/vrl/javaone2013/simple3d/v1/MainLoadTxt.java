/*
 * Copyright 2012 Michael Hoffer <info@michaelhoffer.de>. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY Michael Hoffer <info@michaelhoffer.de> "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL Michael Hoffer <info@michaelhoffer.de> OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of Michael Hoffer <info@michaelhoffer.de>.
 */
package eu.mihosoft.vrl.javaone2013.simple3d.v1;

import eu.mihosoft.vrl.javaone2013.math.TxT2Mesh;
import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;

/**
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class MainLoadTxt extends Application {

    public Group createContent(int details) throws Exception {
        
        Group root = new Group();

//        Sphere sphere = new Sphere(2, details);
//        sphere.setMaterial(new PhongMaterial(Color.RED));
//        sphere.setDrawMode(DrawMode.FILL);
//        root.getChildren().add(sphere);
        
        // Create a sphere
        TriangleMesh mesh = TxT2Mesh.loadTxt(new File("/Users/miho/Dropbox/teapot.txt"));
        
        MeshView meshView = new MeshView(mesh);
        
        meshView.setMaterial(new PhongMaterial(Color.WHITE));
        meshView.setDrawMode(DrawMode.LINE);
        meshView.setCullFace(CullFace.NONE);

        // Add sphere to group
        root.getChildren().add(meshView);


        return root;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = createContent(32);

        // Create camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setFarClip(100);
        camera.setNearClip(0.1);
        
        // and position it
        camera.getTransforms().addAll(
                new Rotate(-20, Rotate.X_AXIS),
                new Translate(0, -2, -14));

        // add camera as node to scene graph
        root.getChildren().add(camera);

        // Setup a scene
        Scene scene = new Scene(root, 400, 400, true, true);
        scene.setCamera(camera);

        //Add the scene to the stage and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
