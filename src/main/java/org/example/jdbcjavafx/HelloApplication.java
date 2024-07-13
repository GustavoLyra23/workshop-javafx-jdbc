package org.example.jdbcjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    private static Scene Mainscene;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("mainview.fxml"));
        ScrollPane scrollPane = fxmlLoader.load();
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        Mainscene = new Scene(scrollPane);
        stage.setTitle("Hello!");
        stage.setScene(Mainscene);
        stage.show();
    }

    public static Scene getMainScene() {
        return Mainscene;
    }


    public static void main(String[] args) {
        launch();
    }
}