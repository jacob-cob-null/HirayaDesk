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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class LoginController implements Initializable {

    @FXML
    private ImageView backgroundImage;

    @FXML
    private VBox rootVBox;

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
            
            Stage stage = (Stage) loginBtn.getScene().getWindow(); // Get the stage here
            
            if (authStatus) {
                // Successful login
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
                Parent dashboardRoot = loader.load();
                Scene dashboardScene = new Scene(dashboardRoot);
                stage.setScene(dashboardScene);
                stage.show();
            } else {
                // Unsuccessful login
                showAlert(Alert.AlertType.ERROR, "Invalid ID or password.", stage);
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "User ID must be a number.", (Stage) loginBtn.getScene().getWindow());
        }
    }

    private void showAlert(Alert.AlertType type, String message, Stage ownerStage) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.initOwner(ownerStage); // Pass the owner stage
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Image image = new Image(getClass().getResource("/image/BG_image.jpg").toExternalForm());
            backgroundImage.setImage(image);

            // Bind image size to VBox size
            backgroundImage.fitWidthProperty().bind(rootVBox.widthProperty());
            backgroundImage.fitHeightProperty().bind(rootVBox.heightProperty());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
