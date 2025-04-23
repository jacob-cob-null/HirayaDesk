/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import static database.ReservationDBcontroller.getOngoingReservation;
import static database.ReservationDBcontroller.getTotalRevenue;
import static database.ReservationDBcontroller.getReservationCount;
import static database.ReservationDBcontroller.getTierDistribution;
import static database.VillaDBcontroller.getAvailableVillaCount;
import static database.VillaDBcontroller.getVillaCount;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.text.Text;

public class DashboardController implements Initializable {

    @FXML
    private PieChart tierChart;

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
        try {
            setValues();
        } catch (SQLException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void setValues() throws SQLException {

        //total reservation
        int totalReservation = getReservationCount();
        reservationTotal.setText(Integer.toString(totalReservation));

        //total revenue
        int totalRevenue = getTotalRevenue();
        revenueTotal.setText(Integer.toString(totalRevenue));

        //total villa
        int totalVilla = getVillaCount();
        villaTotal.setText(Integer.toString(totalVilla));

        //total ongoing reservation
        int ongoingReservation = getOngoingReservation();
        ongoingTotal.setText(Integer.toString(ongoingReservation));

        //tier distribution
        int[] tierDist = getTierDistribution();
        ObservableList<PieChart.Data> tierList = FXCollections.observableArrayList();

        for (int i = 0; i < tierDist.length; i++) {
            String tier = "Tier " + (i+1);
            tierList.add(new PieChart.Data(tier, tierDist[i]));
        }
        tierChart.setData(tierList);
        tierChart.setTitle("Tier Distribution");
        tierChart.setLegendVisible(true);
        tierChart.setLabelsVisible(true);
        
        
        //total available villa
        int availableVilla = getAvailableVillaCount();
        availableTotal.setText(Integer.toString(availableVilla));
    }

}
