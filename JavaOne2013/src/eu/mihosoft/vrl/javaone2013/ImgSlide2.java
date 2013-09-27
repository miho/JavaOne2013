/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013;

/**
 * EXPERIMENTAL.
 * 
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class ImgSlide2 extends TemplateSlide{

    public ImgSlide2(String url) {
        getView().setStyle("-fx-background-image: url(\""+url+"\");-fx-background-size: 100% 100%;");
    }
    
}
