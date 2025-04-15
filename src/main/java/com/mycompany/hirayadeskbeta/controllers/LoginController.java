/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.hirayadeskbeta.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.media.MediaView;
import javafx.fxml.Initializable;
import database.AdminDBcontroller;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Jacob
 */
public class LoginController implements Initializable {

    @FXML
    private MediaView media;

    @FXML
    private MFXTextField usernameField;

    @FXML
    private MFXPasswordField passwordField;

    @FXML
    private MFXButton loginBtn;

    private File file;

    @FXML
    private void handleLogin() throws IOException {
        try {
            int username = Integer.parseInt(usernameField.getText());
            String password = passwordField.getText();
            boolean authStatus = AdminDBcontroller.authenticate(username, password);
            if (authStatus) {
                //successful login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
                Parent dashboardRoot = loader.load();
                Stage stage = (Stage) loginBtn.getScene().getWindow();
                Scene dashboardScene = new Scene(dashboardRoot);
                stage.setScene(dashboardScene);
                stage.show();
            } else {
                //unsuccessful login
                showAlert(Alert.AlertType.ERROR, "Invalid ID or password.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "User ID must be a number.");
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
