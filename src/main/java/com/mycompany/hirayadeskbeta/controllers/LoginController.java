/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import io.github.palexdev.materialfx.controls.MFXTableColumn;
import io.github.palexdev.materialfx.controls.MFXTableView;
import io.github.palexdev.materialfx.controls.cell.MFXTableRowCell;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class LoginController implements Initializable {

    @FXML
    private MFXTableView<Person> tableView;

    @Override
public void initialize(URL location, ResourceBundle resources) {
    MFXTableColumn<Person> nameCol = new MFXTableColumn<>("Name", true);
    nameCol.setRowCellFactory(person -> new MFXTableRowCell<>(Person::getName));

    MFXTableColumn<Person> ageCol = new MFXTableColumn<>("Age", true);
    ageCol.setRowCellFactory(person -> new MFXTableRowCell<>(p -> String.valueOf(p.getAge())));

    tableView.getTableColumns().addAll(nameCol, ageCol);
    tableView.setItems(FXCollections.observableArrayList(
        new Person("Alex", 22),
        new Person("Sam", 30)
    ));
}
}