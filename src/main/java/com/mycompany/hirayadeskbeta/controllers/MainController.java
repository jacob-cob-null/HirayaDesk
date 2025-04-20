/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class MainController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            showDashboard();
        } catch (Exception e) {
            e.printStackTrace(); // Helps catch any load issues
        }
    }
    @FXML
    private AnchorPane contentPane;

    @FXML
    public void showDashboard() throws Exception {
        AnchorPane dashboard = FXMLLoader.load(getClass().getResource("/fxml/Dashboard.fxml"));
        contentPane.getChildren().setAll(dashboard); 
    }

    @FXML
    public void showCalendar() throws Exception {

        AnchorPane calendar = FXMLLoader.load(getClass().getResource("/fxml/Calendar.fxml"));
        contentPane.getChildren().setAll(calendar);

    }

    @FXML
    public void showReservation() throws Exception {

        AnchorPane calendar = FXMLLoader.load(getClass().getResource("/fxml/Reservation.fxml"));
        contentPane.getChildren().setAll(calendar);
    }

    @FXML
    public void showVilla() throws Exception {

        AnchorPane calendar = FXMLLoader.load(getClass().getResource("/fxml/Villa.fxml"));
        contentPane.getChildren().setAll(calendar);
    }

    @FXML
    public void showAbout() throws Exception {

        AnchorPane calendar = FXMLLoader.load(getClass().getResource("/fxml/About.fxml"));
        contentPane.getChildren().setAll(calendar);
    }
}
