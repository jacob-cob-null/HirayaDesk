/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import database.ReservationDBcontroller;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import database.objects.Reservation;
import database.objects.Villa;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class ReservationController implements Initializable {

    @FXML
    private MFXTableView<Reservation> reservationTable;

    @FXML
    private MFXButton createBtn;

    @FXML
    private MFXButton updateBtn;

    @FXML
    private MFXButton deleteBtn;

    @FXML
    private MFXButton newCreate;

    @FXML
    private MFXButton newDelete;

    @FXML
    private MFXButton cancel1;

    @FXML
    private MFXButton cancel3;

    @FXML
    private StackPane createOverlay;

    @FXML
    private StackPane deleteOverlay;

    @FXML
    private MFXComboBox reservationCombo;

    // Observable list to hold Villa objects
    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        try {
            ReservationDBcontroller.mapReservation();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {

        MFXTableColumn<Reservation> idColumn = new MFXTableColumn<>("Reservation ID");
        MFXTableColumn<Reservation> nameColumn = new MFXTableColumn<>("Name");
        MFXTableColumn<Reservation> contactColumn = new MFXTableColumn<>("Contact Number");
        MFXTableColumn<Reservation> villaIDColumn = new MFXTableColumn<>("Villa");
        MFXTableColumn<Reservation> durationColumn = new MFXTableColumn<>("Duration");
        MFXTableColumn<Reservation> startDateColumn = new MFXTableColumn<>("Start Date");
        MFXTableColumn<Reservation> endDateColumn = new MFXTableColumn<>("End Date");
        MFXTableColumn<Reservation> priceColumn = new MFXTableColumn<>("Price");

        idColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getReservationID));
        nameColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustName));
        contactColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustContactNumber));
        villaIDColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getVillaID));
        durationColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getDuration));
        startDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getStartDate));
        endDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getStartDate));
        priceColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getEndDate));

        idColumn.setStyle("-fx-font-size: 16px;");

        reservationTable.setStyle("-fx-font-size: 16px;");

        idColumn.setPrefWidth(80);
        nameColumn.setPrefWidth(160);
        contactColumn.setPrefWidth(130);
        villaIDColumn.setPrefWidth(70);
        durationColumn.setPrefWidth(70);
        startDateColumn.setPrefWidth(120);
        endDateColumn.setPrefWidth(120);
        priceColumn.setPrefWidth(110);

        reservationTable.getTableColumns().addAll(idColumn, nameColumn, contactColumn, villaIDColumn, durationColumn, startDateColumn, endDateColumn, priceColumn);
    }

    private void refreshTable() {
        reservationData.clear();
        reservationData.addAll(ReservationDBcontroller.rawReservationData);
        reservationTable.setItems(reservationData);
    }

}
