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
public class VillaController implements Initializable {

    @FXML
    private MFXButton newRecord;
    @FXML
    private MFXComboBox tierCombo;
    @FXML
    private MFXTableView villaTable;
    @FXML
    private MFXButton deleteBtn;
    @FXML
    private MFXButton updateBtn;
    @FXML
    private MFXButton createBtn;
    @FXML
    private StackPane createOverlay;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
