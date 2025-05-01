package com.mycompany.hirayadeskbeta.controllers;

import database.ReservationDBcontroller;
import static database.ReservationDBcontroller.rawReservationData;
import static database.ReservationDBcontroller.updateStatus;
import database.VillaDBcontroller;
import static database.VillaDBcontroller.updateVillaStatus;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTableView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;
import database.objects.Reservation;
import database.objects.VillaComboItem;
import io.github.palexdev.materialfx.controls.MFXDatePicker;
import io.github.palexdev.materialfx.controls.MFXFilterComboBox;
import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import io.github.palexdev.materialfx.filter.IntegerFilter;
import io.github.palexdev.materialfx.filter.StringFilter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private MFXFilterComboBox<Integer> reservationCombo;

    @FXML
    private MFXTextField nameInput;

    @FXML
    private MFXTextField contactInput;

    @FXML
    private MFXFilterComboBox<VillaComboItem> villaCombo;

    @FXML
    private MFXTextField durationInput;

    @FXML
    private MFXDatePicker dateInput;

    @FXML
    private MFXFilterComboBox<Integer> reservationCombo2;

    @FXML
    private MFXTextField updateName;

    @FXML
    private MFXTextField updateContact;

    @FXML
    private MFXFilterComboBox<VillaComboItem> updateVilla;

    @FXML
    private MFXTextField updateDuration;

    @FXML
    private MFXDatePicker updateDate;

    // Observable list to hold Villa objects
    private ObservableList<Reservation> reservationData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupListeners();
        setupTable();
        try {
            ReservationDBcontroller.mapReservation();
            updateStatus();
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
        MFXTableColumn<Reservation> statusColumn = new MFXTableColumn<>("Status", true);

        idColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getReservationID));
        nameColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustName));
        contactColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getCustContactNumber));
        villaIDColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getVillaID));
        durationColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getDuration));
        startDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getStartDate));
        endDateColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getEndDate));
        priceColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getPrice));
        statusColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Reservation::getStatus));

        reservationTable.getFilters().addAll(
                new StringFilter<>("Name", Reservation::getCustName),
                new IntegerFilter<>("Duration", Reservation::getDuration),
                new IntegerFilter<>("Price", Reservation::getPrice),
                new StringFilter<>("Status", Reservation::getStatus)
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
        statusColumn.setPrefWidth(120);

        reservationTable.getTableColumns().addAll(idColumn, nameColumn, contactColumn, villaIDColumn, durationColumn, startDateColumn, endDateColumn, priceColumn, statusColumn);
    }

    private void setupListeners() {
        // CREATE 
        createBtn.setOnAction(event -> {
            VillaController.loadVillaIDsToComboBox(villaCombo);  // Now compatible with VillaComboItem
            createOverlay.setVisible(true);
        });
        cancel1.setOnAction(event -> {
            createOverlay.setVisible(false);
        });

        newCreate.setOnAction(event -> {
            refreshTable();
            String name = nameInput.getText();
            String contact = contactInput.getText();

            VillaComboItem selectedVilla = villaCombo.getValue();
            String durationStr = durationInput.getText();
            String startDateStr = dateInput.getText();

            // Validate inputs
            if (name == null || name.isEmpty()
                    || contact == null || contact.isEmpty()
                    || selectedVilla == null
                    || durationStr == null || durationStr.isEmpty()
                    || startDateStr == null || startDateStr.isEmpty()) {

                Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "Missing Fields");
                return;
            }

            // Get the villa ID from the VillaComboItem
            Integer villa = selectedVilla.getVillaID();
            
            // Convert duration and startDateStr to their respective types
            int duration = Integer.parseInt(durationStr);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

            // Parse the start date using the correct format
            LocalDate startDate = LocalDate.parse(startDateStr, formatter);

            ReservationDBcontroller.createReservation(name, contact, villa, duration, startDate);

            try {
                VillaDBcontroller.updateVillaStatus(villa, 0);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            createOverlay.setVisible(false);
            VillaController.loadVillaIDsToComboBox(villaCombo);
            villaCombo.getSelectionModel().clearSelection();
            refreshTable();
            nameInput.clear();
            contactInput.clear();
            durationInput.clear();
            dateInput.clear();
            try {
                ReservationDBcontroller.mapReservation();
                refreshTable();
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        //UPDATE
        updateBtn.setOnAction(event -> {
            loadReservationIDsToComboBox(reservationCombo2);
            VillaController.loadVillaIDsToComboBox(updateVilla);
            updateOverlay.setVisible(true);
        });
        cancel2.setOnAction(event -> {
            updateOverlay.setVisible(false);
        });
        newUpdate.setOnAction(event -> {
            Integer id = (Integer) reservationCombo2.getValue();
            String name = updateName.getText();
            String contact = updateContact.getText();
            VillaComboItem selectedVilla = updateVilla.getValue();
            String durationStr = updateDuration.getText();
            String startDateStr = updateDate.getText();

            // Validate inputs
            if (id == null || name == null || name.isEmpty()
                    || contact == null || contact.isEmpty()
                    || selectedVilla == null
                    || durationStr == null || durationStr.isEmpty()
                    || startDateStr == null || startDateStr.isEmpty()) {

                Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "Missing Fields");
                return;
            }
            
            // Get the villa ID from the VillaComboItem
            Integer villa = selectedVilla.getVillaID();
            
            Integer oldVilla = null;
            for (Reservation res : rawReservationData) {
                if (res.getReservationID() == id) {
                    oldVilla = res.getVillaID();
                }
            }

            int duration = Integer.parseInt(durationStr);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM d, yyyy");

            LocalDate startDate = LocalDate.parse(startDateStr, formatter);

            ReservationDBcontroller.updateReservation(id, name, contact, villa, duration, startDate);
            if (oldVilla != villa) {
                try {
                    VillaDBcontroller.updateVillaStatus(oldVilla, 1);
                } catch (SQLException ex) {
                    Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            try {
                VillaDBcontroller.updateVillaStatus(villa, 0);
            } catch (SQLException ex) {
                Logger.getLogger(ReservationController.class.getName()).log(Level.SEVERE, null, ex);
            }
            updateOverlay.setVisible(false);
            loadReservationIDsToComboBox(reservationCombo2);
            VillaController.loadVillaIDsToComboBox(updateVilla);
            refreshTable();
            reservationCombo2.getSelectionModel().clearSelection();
            updateVilla.getSelectionModel().clearSelection();
            updateName.clear();
            updateContact.clear();
            updateDuration.clear();
            updateDate.clear();
        });

        //DELETE
        deleteBtn.setOnAction(event -> {
            deleteOverlay.setVisible(true);
            loadReservationIDsToComboBox(reservationCombo);

        });
        cancel3.setOnAction(event -> {
            deleteOverlay.setVisible(false);
        });
        newDelete.setOnAction(event -> {
            Integer selectedID = (Integer) reservationCombo.getValue();
            if (selectedID == null) {
                Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "No Reservation selected for deletion.");
                return;
            }

            try {
                // Find the reservation by ID
                Reservation reservationToDelete = null;
                for (Reservation reservation : ReservationDBcontroller.rawReservationData) {
                    if (reservation.getReservationID() == selectedID) {
                        reservationToDelete = reservation;
                        break;
                    }
                }

                if (reservationToDelete == null) {
                    Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "Reservation with ID {0} not found.", selectedID);
                    return;
                }

                // Delete the reservation
                ReservationDBcontroller.deleteReservation(selectedID);

                // Update the villa status (assuming the villaID is from the reservation object)
                updateVillaStatus(reservationToDelete.getVillaID(), 1);  // Set status back to available (1)

                // Hide the overlay and refresh the UI components
                deleteOverlay.setVisible(false);
                loadReservationIDsToComboBox(reservationCombo);  // Refresh combo box
                reservationCombo.getSelectionModel().clearSelection();  // Clear selection
                refreshTable();  // Refresh the reservation table
            } catch (SQLException ex) {
                Logger.getLogger(VillaController.class.getName()).log(Level.SEVERE, "Error while deleting reservation with ID: " + selectedID, ex);
            }
        });
    }

    private void loadReservationIDsToComboBox(MFXFilterComboBox<Integer> combo) {
        if (combo == null) {
            Logger.getLogger(ReservationController.class.getName()).log(Level.WARNING, "ComboBox is null in loadReservationIDsToComboBox");
            return;
        }

        try {
            List<Integer> reservationIDs = ReservationDBcontroller.getAllReservationIDs();
            combo.setItems(FXCollections.observableArrayList(reservationIDs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        reservationData.clear();
        reservationData.addAll(ReservationDBcontroller.rawReservationData);
        reservationTable.setItems(reservationData);
    }
}