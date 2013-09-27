/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.math;

import javafx.geometry.Point3D;

/**
 * Simple node class (contains nod eindex and coordinates).
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Node {

    /**
     * node index
     */
    private int index;

    /**
     * node/vertex coordinates
     */
    private Point3D point;

    /**
     * Constructor.
     */
    public Node() {
    }

    /**
     * Constructor.
     *
     * @param index node index
     * @param point node coordinates
     */
    public Node(int index, Point3D point) {
        this.index = index;
        this.point = point;
    }

    /**
     * @return the node index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the node index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the node point/coordinates
     */
    public Point3D getPoint() {
        return point;
    }

    /**
     * @param point the node point/coordinates to set
     */
    public void setPoint(Point3D point) {
        this.point = point;
    }
}
