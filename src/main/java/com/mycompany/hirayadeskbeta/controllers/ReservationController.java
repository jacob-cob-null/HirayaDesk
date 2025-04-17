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
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.sql.SQLException;
import java.util.Comparator;
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
    private MFXButton newUpdate;

    @FXML
    private MFXButton cancel1;

    @FXML
    private MFXButton cancel3;

    @FXML
    private MFXButton cancel2;

    @FXML
    private StackPane createOverlay;

    @FXML
    private StackPane deleteOverlay;

    @FXML
    private StackPane updateOverlay;

    @FXML
    private MFXComboBox reservationCombo;

    @FXML
    private MFXComboBox reservationCombo2;

    // Observable list to hold Villa objects
    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupListeners();
        setupTable();
        try {
            ReservationDBcontroller.mapReservation();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {

        MFXTableColumn<Reservation> idColumn = new MFXTableColumn<>("Reservation ID", true);
        MFXTableColumn<Reservation> nameColumn = new MFXTableColumn<>("Name", true);
        MFXTableColumn<Reservation> contactColumn = new MFXTableColumn<>("Contact Number", true);
        MFXTableColumn<Reservation> villaIDColumn = new MFXTableColumn<>("Villa", true);
        MFXTableColumn<Reservation> durationColumn = new MFXTableColumn<>("Duration", true);
        MFXTableColumn<Reservation> startDateColumn = new MFXTableColumn<>("Start Date", true);
        MFXTableColumn<Reservation> endDateColumn = new MFXTableColumn<>("End Date", true);
        MFXTableColumn<Reservation> priceColumn = new MFXTableColumn<>("Price", true);

        idColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getReservationID));
        nameColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustName));
        contactColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustContactNumber));
        villaIDColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getVillaID));
        durationColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getDuration));
        startDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getStartDate));
        endDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getEndDate));
        priceColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getPrice));

        reservationTable.getFilters().addAll(
                new IntegerFilter<>("Duration", Reservation::getDuration),
                new IntegerFilter<>("Price", Reservation::getPrice)
        );

        reservationTable.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #1B4137;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;"
                + "-fx-border-width: 3px;"
                + "-fx-padding: 30px;"
                + "-fx-font-size: 20px;"
        );

        //cell width
        idColumn.setPrefWidth(150);
        nameColumn.setPrefWidth(160);
        contactColumn.setPrefWidth(180);
        villaIDColumn.setPrefWidth(100);
        durationColumn.setPrefWidth(100);
        startDateColumn.setPrefWidth(170);
        endDateColumn.setPrefWidth(170);
        priceColumn.setPrefWidth(160);

        reservationTable.getTableColumns().addAll(idColumn, nameColumn, contactColumn, villaIDColumn, durationColumn, startDateColumn, endDateColumn, priceColumn);
    }

    private void setupListeners() {
        //CREATE 
        createBtn.setOnAction(event -> {
            createOverlay.setVisible(true);
        });
        cancel1.setOnAction(event -> {
            createOverlay.setVisible(false);
        });
        //UPDATE
        updateBtn.setOnAction(event -> {
            updateOverlay.setVisible(true);
        });
        cancel2.setOnAction(event -> {
            updateOverlay.setVisible(false);
        });
        //DELETE
        deleteBtn.setOnAction(event -> {
            deleteOverlay.setVisible(true);
        });
        cancel3.setOnAction(event -> {
            deleteOverlay.setVisible(false);
        });
    }

    private void refreshTable() {
        reservationData.clear();
        reservationData.addAll(ReservationDBcontroller.rawReservationData);
        reservationTable.setItems(reservationData);
    }

}
