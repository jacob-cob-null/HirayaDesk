package com.mycompany.hirayadeskbeta;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Main.fxml"));
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
        launch(args);
    }

}
