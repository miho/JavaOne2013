/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.mihosoft.vrl.javaone2013.slides;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Michael Hoffer &lt;info@michaelhoffer.de&gt;
 */
public class Controls2DSlideController implements Initializable {

    @FXML
    private AnchorPane root;
    @FXML
    private LineChart<Number, Number> linechart;
    @FXML
    private Label label;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        label.setText("One Framework To Rule Them All\n\n2D API (Controls, Charts)");

        XYChart.Series series = new XYChart.Series();
        series.setName("f(x)");

        series.getData().addAll(
                new XYChart.Data(1, 1),
                new XYChart.Data(2, 2),
                new XYChart.Data(3, 4),
                new XYChart.Data(4, 0.5),
                new XYChart.Data(5, 1));

        linechart.getData().add(series);
    }

}
