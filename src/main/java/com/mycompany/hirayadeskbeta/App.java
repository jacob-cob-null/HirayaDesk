package com.mycompany.hirayadeskbeta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import database.MainDB;
import database.AdminDBcontroller;
import java.util.Scanner;

import java.io.IOException;

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
            scene.getStylesheets().add(getClass().getResource("/fxml/Style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(true); // Fullscreen mode
            primaryStage.setFullScreenExitHint("");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        MainDB.connect();
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter admin ID: ");
        int username = sc.nextInt();
        sc.nextLine(); // Consume the newline character

        System.out.print("Enter password: ");
        String password = sc.nextLine();
        String result = AdminDBcontroller.authenticate(username, password);
        System.out.println(result);
//        //launch();
    }

}
