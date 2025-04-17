/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class ReservationController implements Initializable {


    @FXML 
    private MFXTableView reservationTable;
    
    @FXML
    private MFXButton createBtn ;
    private MFXButton updateBtn;
    private MFXButton deleteBtn;
    
    private MFXButton newCreate;
    private MFXButton newDelete;
    
    private MFXButton cancel1;
    private MFXButton cancel3;
    
    private StackPane createOverlay;
    private StackPane deleteOverlay;
    
    private MFXComboBox reservationCombo;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
