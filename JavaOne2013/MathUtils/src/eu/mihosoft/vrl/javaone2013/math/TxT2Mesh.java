/* 
 * TxT2Geometry.java
 * 
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2009–2012 Steinbeis Forschungszentrum (STZ Ölbronn),
 * Copyright (c) 2006–2012 by Michael Hoffer
 * 
 * This file is part of Visual Reflection Library (VRL).
 *
 * VRL is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.
 * 
 * see: http://opensource.org/licenses/LGPL-3.0
 *      file://path/to/VRL/src/eu/mihosoft/vrl/resources/license/lgplv3.txt
 *
 * VRL is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * This version of VRL includes copyright notice and attribution requirements.
 * According to the LGPL this information must be displayed even if you modify
 * the source code of VRL. Neither the VRL Canvas attribution icon nor any
 * copyright statement/attribution may be removed.
 *
 * Attribution Requirements:
 *
 * If you create derived work you must do three things regarding copyright
 * notice and author attribution.
 *
 * First, the following text must be displayed on the Canvas:
 * "based on VRL source code". In this case the VRL canvas icon must be removed.
 * 
 * Second, the copyright notice must remain. It must be reproduced in any
 * program that uses VRL.
 *
 * Third, add an additional notice, stating that you modified VRL. In addition
 * you must cite the publications listed below. A suitable notice might read
 * "VRL source code modified by YourName 2012".
 * 
 * Note, that these requirements are in full accordance with the LGPL v3
 * (see 7. Additional Terms, b).
 *
 * Publications:
 *
 * M. Hoffer, C.Poliwoda, G.Wittum. Visual Reflection Library -
 * A Framework for Declarative GUI Programming on the Java Platform.
 * Computing and Visualization in Science, 2011, in press.
 */
package eu.mihosoft.vrl.javaone2013.math;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import javafx.geometry.Point3D;
import javafx.scene.shape.TriangleMesh;

/**
 * <p>
 * Converts simple text files to java 3d geometries.</p>
 * <p>
 * The format is relatively simple:
 * </p>
 * <code>
 * <pre>
 * &#35;nodes &#35;triangles
 * node_index node_x node_y node_z
 * .
 * .
 * triangle_index node_index_1 node_index_2 node_index_3
 * .
 * .
 * </pre>
 * </code>
 * <p>
 * Example (tetrahedron):
 * </p>
 * <code>
 * <pre>
 * 4 4
 * 0  1  1 -1
 * 1 -1 -1 -1
 * 2 -1  1  1
 * 3  1 -1  1
 * 0 0 1 2
 * 1 0 1 3
 * 2 0 2 3
 * 3 1 2 3
 * </pre>
 * </code>
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 * @see VTriangleArray
 */
public class TxT2Mesh implements Serializable {

    private static final long serialVersionUID = 3445929541515695754L;

    private TxT2Mesh() {
        throw new AssertionError("don't instantiate me!");
    }
    
    

    /**
     * Loads text file to a geometry array.
     *
     * @param file the file
     * @return the geometry
     * @throws java.io.IOException
     */
    public static TriangleMesh loadTxt(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));

        StringTokenizer stringTokenizer
                = new StringTokenizer(reader.readLine());

        int numberOfNodes = Integer.parseInt(stringTokenizer.nextToken());
        int numberOfTriangles = Integer.parseInt(stringTokenizer.nextToken());

        System.out.println("#Nodes: " + numberOfNodes);
        System.out.println("#Triangles: " + numberOfTriangles);

        List<Node> nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            Node n = readNode(stringTokenizer);
            nodes.add(n);
        }

        List<Triangle> triangles = new ArrayList<>();

        for (int i = 0; i < numberOfTriangles; i++) {
            stringTokenizer = new StringTokenizer(reader.readLine());
            Triangle t = readTriangle(stringTokenizer);

            triangles.add(t);
        }

        return MathUtil.toTriangleMesh(triangles, nodes, 1.f, 1.f, 1.f);
    }

    /**
     * Reads a node.
     *
     * @param stringTokenizer the string tokenizer to use for reading
     * @return the node
     * @throws java.io.IOException
     */
    private static Node readNode(StringTokenizer stringTokenizer) throws IOException {

        int index = Integer.parseInt(stringTokenizer.nextToken());

        float x = Float.parseFloat(stringTokenizer.nextToken());
        float y = -Float.parseFloat(stringTokenizer.nextToken());
        float z = Float.parseFloat(stringTokenizer.nextToken());

        return new Node(index, new Point3D(x, y, z));
    }

    /**
     * Reads a triangle.
     *
     * @param stringTokenizer the string tokenizer to use for reading
     * @return the read triangle
     * @throws java.io.IOException
     */
    private static Triangle readTriangle(
            StringTokenizer stringTokenizer) throws IOException {

        Triangle t = new Triangle();

        t.setIndex(Integer.parseInt(stringTokenizer.nextToken()));

        t.setxIndex(Integer.parseInt(stringTokenizer.nextToken()));
        t.setyIndex(Integer.parseInt(stringTokenizer.nextToken()));
        t.setzIndex(Integer.parseInt(stringTokenizer.nextToken()));

        return t;
    }
}
