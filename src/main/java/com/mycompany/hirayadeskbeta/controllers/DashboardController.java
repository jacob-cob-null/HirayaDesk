/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {

    @FXML
    private PieChart chart;

    @FXML
    private Text reservationTotal;

    @FXML
    private Text villaTotal;

    @FXML
    private Text revenueTotal;

    @FXML
    private Text availableTotal;

    @FXML
    private Text ongoingTotal;
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
