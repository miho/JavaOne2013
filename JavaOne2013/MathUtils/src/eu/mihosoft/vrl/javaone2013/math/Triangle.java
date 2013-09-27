/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eu.mihosoft.vrl.javaone2013.math;

/**
 * A simple triangle class (contains vertex indices only, not the coordinates).
 * 
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Triangle {
    
    /**
     * triangle index
     */
    private int index;
    
    /**
     * first vertex index
     */
    private int xIndex;
    
    /**
     * second vertex index
     */
    private int yIndex;
    
    /**
     * third vertex index
     */
    private int zIndex;

    /**
     * Constructor.
     */
    public Triangle() {
    }

    /**
     * Constructor.
     * @param index triangle index
     * @param xIndex first vertex index
     * @param yIndex second vertex index
     * @param zIndex third vertex index
     */
    public Triangle(int index, int xIndex, int yIndex, int zIndex) {
        this.index = index;
        this.xIndex = xIndex;
        this.yIndex = yIndex;
        this.zIndex = zIndex;
    }

    /**
     * @return the index
     */
    public int getIndex() {
        return index;
    }

    /**
     * @param index the index to set
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * @return the first vertex index
     */
    public int getxIndex() {
        return xIndex;
    }

    /**
     * @param xIndex the index to set (first index)
     */
    public void setxIndex(int xIndex) {
        this.xIndex = xIndex;
    }

    /**
     * @return the second vertex index
     */
    public int getyIndex() {
        return yIndex;
    }

    /**
     * @param yIndex the index to set (second index)
     */
    public void setyIndex(int yIndex) {
        this.yIndex = yIndex;
    }

    /**
     * @return the third vertex index
     */
    public int getzIndex() {
        return zIndex;
    }

    /**
     * @param zIndex the index to set (third index)
     */
    public void setzIndex(int zIndex) {
        this.zIndex = zIndex;
    }
    
    
}
