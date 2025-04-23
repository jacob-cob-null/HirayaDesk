package com.mycompany.hirayadeskbeta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.MainDB;
import database.AdminDBcontroller;
import static database.ReservationDBcontroller.mapReservation;
import static database.VillaDBcontroller.mapVilla;
import java.util.Scanner;

import java.io.IOException;
import java.sql.SQLException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setTitle("Hiraya Abode");
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true); // Fullscreen mode
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException {
        mapReservation();
        mapVilla();
        launch();
    }

}
