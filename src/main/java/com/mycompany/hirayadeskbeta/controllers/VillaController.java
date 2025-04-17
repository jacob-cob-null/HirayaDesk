package com.mycompany.hirayadeskbeta.controllers;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.StackPane;

import database.VillaDBcontroller;  // Import VillaDBcontroller
import database.objects.Villa;    // Import Villa class
import io.github.palexdev.materialfx.filter.StringFilter;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class VillaController implements Initializable {

    @FXML
    private MFXTableView<Villa> villaTable;

    @FXML
    private MFXButton deleteBtn;

    @FXML
    private MFXButton updateBtn;

    @FXML
    private MFXButton createBtn;

    @FXML
    private StackPane createOverlay;

    @FXML
    private StackPane deleteOverlay;
    @FXML
    private MFXButton cancel1;
    @FXML
    private MFXButton cancel2;

    @FXML
    private MFXComboBox<String> tierCombo;

    @FXML
    private MFXComboBox<Integer> villaCombo;

    @FXML
    private MFXButton newRecord;

    @FXML
    private MFXButton newDelete;

    // Observable list to hold Villa objects
    private ObservableList<Villa> villaData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setupTable();
        setupListeners();

        try {
            VillaDBcontroller.mapVilla();
            refreshTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupTable() {

        MFXTableColumn<Villa> idColumn = new MFXTableColumn<>("Villa ID");
        MFXTableColumn<Villa> tierColumn = new MFXTableColumn<>("Tier");
        MFXTableColumn<Villa> availableColumn = new MFXTableColumn<>("Availability");

        idColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Villa::getVillaID));
        tierColumn.setRowCellFactory(v -> new MFXTableRowCell<>(Villa::getTierID));
        availableColumn.setRowCellFactory(v -> new MFXTableRowCell<>(villa -> villa.isAvailable() ? "Available" : "Not Available"));

        villaTable.setStyle(
                "-fx-background-color: white;"
                + "-fx-border-color: #1B4137;"
                + "-fx-border-radius: 10px;"
                + "-fx-background-radius: 10px;"
                + "-fx-border-width: 3px;"
                + "-fx-padding: 30px;"
                + "-fx-font-size: 20px;"
        );

        //Cell width
        idColumn.setPrefWidth(120);
        tierColumn.setPrefWidth(200);
        availableColumn.setPrefWidth(200);

        villaTable.getTableColumns().addAll(idColumn, tierColumn, availableColumn);


        villaTable.getFilters().addAll(
                new StringFilter<>("Tier", Villa::getTierID),
                new StringFilter<>("Availability", villa -> villa.isAvailable() ? "Available" : "Not Available")
        );

    }

    private void setupListeners() {
        createBtn.setOnAction(event -> {
            loadTierIDsToComboBox();
            createOverlay.setVisible(true);
        });

        //CREATING VILLA
        newRecord.setOnAction(event -> {
            String selectedID = tierCombo.getValue();
            if (selectedID == null) {
                Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "No Villa selected for deletion.");
                return;
            }
            try {
                VillaDBcontroller.createVilla(selectedID);
                createOverlay.setVisible(false);
                loadTierIDsToComboBox();
                tierCombo.getSelectionModel().clearSelection();
                refreshTable();

            } catch (SQLException ex) {
                Logger.getLogger(VillaController.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        deleteBtn.setOnAction(event -> {
            loadVillaIDsToComboBox();
            deleteOverlay.setVisible(true);
        });
        cancel1.setOnAction(event -> {
            createOverlay.setVisible(false);
        });
        cancel2.setOnAction(event -> {
            deleteOverlay.setVisible(false);
        });

        //DELETING
        newDelete.setOnAction(event -> {
            Integer selectedID = villaCombo.getValue();
            if (selectedID == null) {
                Logger.getLogger(VillaController.class.getName()).log(Level.WARNING, "No Villa selected for deletion.");
                return;
            }
            try {
                VillaDBcontroller.deleteVilla(selectedID);
                deleteOverlay.setVisible(false);
                loadVillaIDsToComboBox();
                villaCombo.getSelectionModel().clearSelection();
                refreshTable();
            } catch (SQLException ex) {
                Logger.getLogger(VillaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    private void loadVillaIDsToComboBox() {
        try {
            List<Integer> villaIDs = VillaDBcontroller.getAllVillaIDs();
            villaCombo.setItems(FXCollections.observableArrayList(villaIDs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadTierIDsToComboBox() {
        try {
            List<String> tierIDs = VillaDBcontroller.getAllTierIDs();
            tierCombo.setItems(FXCollections.observableArrayList(tierIDs));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void refreshTable() {
        villaData.clear();
        villaData.addAll(VillaDBcontroller.rawVillaData);
        villaTable.setItems(villaData);
    }
}
