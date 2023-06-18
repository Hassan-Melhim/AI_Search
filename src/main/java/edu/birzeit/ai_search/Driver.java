package edu.birzeit.ai_search;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Driver extends Application {
    public static Control control;
    ArrayList<City> path;
    Palestine graph;

    @Override
    public void start(Stage stage) throws IOException {
        URL path = getClass().getResource("/hello-view.fxml");
        FXMLLoader loader = new FXMLLoader(path);
        Parent root = loader.load();
        control = loader.getController();

        String file = "src\\main\\resources\\cities.csv";
        ArrayList<City> cityTemp = new ArrayList<>();
        BufferedReader reader = null;
        String line = "";


        try{
            reader = new BufferedReader(new FileReader(file));
            while((line = reader.readLine()) != null){
                String[] row = line.split(",");
                cityTemp.add(new City(Double.parseDouble(row[1]),
                        Double.parseDouble(row[2]), row[0]));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            reader.close();
        }

        Scene scene = new Scene(root);
        stage.setTitle("Search Algorithms!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public ArrayList<City> calcPath (City current){
        this.path  = new ArrayList<City>();
        while (current != null){
            path.add(current);
            current = current.getParent();
        }
        return path;
    }
}