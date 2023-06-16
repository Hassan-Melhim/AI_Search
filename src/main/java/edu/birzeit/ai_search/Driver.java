package edu.birzeit.ai_search;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Driver extends Application {
    public static Control control;
    ArrayList<City> Path = new ArrayList<City>();

    @Override
    public void start(Stage stage) throws IOException {
        URL path = getClass().getResource("/hello-view.fxml");
        FXMLLoader loader = new FXMLLoader(path);
        Parent root = loader.load();
        control = loader.getController();

        Scene scene = new Scene(root);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}