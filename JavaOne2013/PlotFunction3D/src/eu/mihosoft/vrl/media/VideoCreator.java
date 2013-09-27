/* 
 * VideoCreator.java
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
package eu.mihosoft.vrl.media;

import eu.mihosoft.vrl.ext.com.bric.qt.JPEGMovieAnimation;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;

/**
 * Creates uncompressed video files (mov) from image files.
 *
 * @author Michael Hoffer <info@michaelhoffer.de>
 */
public class VideoCreator implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Creates a movie animation.
     * @param output video file location
     * @return movie animation
     * @throws IOException if the specified can not be created
     */
    public static JPEGMovieAnimation create(File output) throws IOException {

        if (!output.getName().toLowerCase().endsWith(".m4v")) {
            output = new File(output.getAbsoluteFile().getParent(),
                    output.getName() + ".m4v");
        }

        return new JPEGMovieAnimation(output);
    }

    /**
     * Adds a frame to the specified animation.
     * @param anim animation
     * @param img image to add
     * @param spf seconds per frame
     */
    public static void addFrame(JPEGMovieAnimation anim, javafx.scene.image.Image img, float spf) {

        BufferedImage bImg = SwingFXUtils.fromFXImage(img, null);
        try {
            bImg = convertToBufferedImage(
                    bImg, BufferedImage.TYPE_INT_RGB);
            anim.addFrame(spf, bImg, 0.9f);
        } catch (IOException ex) {
            Logger.getLogger(VideoCreator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Converts a set of images to a video file (mov).
     *
     *
     * @param imgs input image list to convert
     * @param output output file
     * @param spf seconds per frame
     *
     * @throws IOException
     */
    public void convert(List<WritableImage> imgs,
            File output,
            float spf)
            throws IOException {

        String msg = ">> Creating Movie file: " + output;
        System.out.println(msg);

        if (!output.getName().toLowerCase().endsWith(".mov")) {
            output = new File(output.getAbsoluteFile().getParent(),
                    output.getName() + ".mov");
        }

        JPEGMovieAnimation anim = null;
        try {
            anim = new JPEGMovieAnimation(output);
        } catch (IOException ex) {
            Logger.getLogger(VideoCreator.class.getName()).
                    log(Level.SEVERE, null, ex);
        }

        if (imgs.isEmpty()) {
            msg = "--> Error: no images found in " + output;
            System.out.print(msg);

            return;
        }

        for (int i = 0; i < imgs.size(); i++) {

            msg = "--> processing image: " + (i + 1) + "/" + imgs.size();
            System.out.print(msg);

            BufferedImage img = SwingFXUtils.fromFXImage(imgs.get(i), null);

//            img = convertToBufferedImage(
//                    img, BufferedImage.TYPE_INT_RGB);
            anim.addFrame(spf, img, 0.9f);

            System.out.println("\t[Done]");

        }

        anim.close();

    }

    /**
     * Converts a buffered image to different image type and scales it. A
     * possible use case is the convertion of an image with alpha channel
     * (BufferedImage.TYPE_INT_ARGB) to an image with only Rgb color channels
     * (BufferedImage.TYPE_INT_RGB).
     *
     * @param image the image to convert
     * @param imageType the image type to use for convertion
     * @param w the width of the converted image
     * @param h the height of the converted image
     * @param fixedRatio defines whether ratio is fixed
     * @return the converted image
     */
    public static BufferedImage convert(Image image,
            int imageType, int w, int h, boolean fixedRatio) {

        BufferedImage previewImage = new BufferedImage(w,
                h,
                imageType);
        Graphics2D g2 = previewImage.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // *******************************
        // fixed aspect ratio
        // *******************************
        if (fixedRatio) {
            double scalex = 1;
            double scaley = 1;

            double sx = (double) w / image.getWidth(null);
            double sy = (double) h / image.getHeight(null);
            scalex = Math.min(sx, sy);
            scaley = scalex;
            // center the image
            g2.translate((w - (image.getWidth(null) * scalex)) / 2,
                    (h - (image.getHeight(null) * scaley)) / 2);

            g2.scale(scalex, scaley);

            g2.drawImage(image, 0, 0, null);

        } else {
            g2.drawImage(image, 0, 0, w, h, null);
        }

        g2.dispose();

        return previewImage;

    }

    /**
     * Converts an image to a buffered image with specified color model/format.
     *
     * @param image the image to convert
     * @param imageType image color format/model
     * @return the converted image
     */
    public static BufferedImage convertToBufferedImage(
            BufferedImage image, int imageType) {
        BufferedImage result = new BufferedImage(image.getWidth(),
                image.getHeight(), imageType);
        Graphics2D g2 = result.createGraphics();
        g2.drawImage(image, null, 0, 0);
        return result;
    }

    /**
     * Recursively returns files that end with at least one of the specified
     * endings.
     * <p>
     * <b>Note</b>Folders are not considered. Thus, the resulting collection
     * only contains files.</p>
     *
     * @param location folder to search
     * @param endings endings
     */
    public static ArrayList<File> listFiles(
            File sourceLocation, String[] endings) {
        ArrayList<File> result = new ArrayList<File>();

        _getFilesRecursive(sourceLocation, result, endings);

        return result;
    }

    /**
     * Returns files that end with at least one of the specified endings.
     *
     * @param location folder to search
     * @param files files
     * @param endings endings
     */
    private static void _getFilesRecursive(
            File location, Collection<File> files, String[] endings) {

        if (location.isDirectory()) {

            String[] children = location.list();
            for (int i = 0; i < children.length; i++) {
                _getFilesRecursive(
                        new File(location, children[i]), files, endings);
            }
        } else {
            // sourcelocation  is file now
            for (String ending : endings) {
                if (location.getAbsolutePath().endsWith(ending)) {
                    files.add(location);
                    break;
                }
            }
        }
    }
}
