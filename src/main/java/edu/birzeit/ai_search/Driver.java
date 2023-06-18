package edu.birzeit.ai_search;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

public class Driver extends Application {
    @FXML
    AnchorPane pane;
    public static Control control;
    ArrayList<City> path;
    Palestine graph;

    @Override
    public void start(Stage stage) throws IOException {
        URL path = getClass().getResource("/hello-view.fxml");
        FXMLLoader loader = new FXMLLoader(path);
        Parent root = loader.load();
        control = loader.getController();

        //read file for cities
        String file = "src\\main\\resources\\cities.csv";
        ArrayList<City> cityTemp = new ArrayList<>();
        BufferedReader cityReader = null;
        String cityLine = "";
        try{
            cityReader = new BufferedReader(new FileReader(file));
            while((cityLine = cityReader.readLine()) != null){
                String[] row = cityLine.split(",");
                Circle temp = new Circle(Double.parseDouble(row[1]),
                        Double.parseDouble(row[2]), 3);
                cityTemp.add(new City(Double.parseDouble(row[1]),
                        Double.parseDouble(row[2]), row[0], temp));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            cityReader.close();
        }

        //read files for roads
        String actualfile = "src\\main\\resources\\actual.csv";
        String airfile = "src\\main\\resources\\air.csv";
        ArrayList<Road> roadTemp = new ArrayList<>();
        BufferedReader actualReader = null;
        BufferedReader airReader = null;
        String actualLine = "";
        String airLine = "";
        try{
            actualReader = new BufferedReader(new FileReader(actualfile));
            airReader = new BufferedReader(new FileReader(airfile));
            while((actualLine = actualReader.readLine()) != null){
                airLine = airReader.readLine();
                String[] actualRow = actualLine.split(",");
                String[] airRow = airLine.split(",");

                roadTemp.add(new Road(findCity(airRow[0], cityTemp), findCity(airRow[1], cityTemp)
                        , Double.parseDouble(actualRow[2]), Double.parseDouble(airRow[2])));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            actualReader.close();
            airReader.close();
        }

        this.graph = new Palestine(cityTemp,roadTemp);

        //generate dots
        for (int i = 0; i < cityTemp.size(); i++) {
            cityTemp.get(i).c.setFill(Color.RED);
            pane.getChildren().addAll(cityTemp.get(i).c, root);
        }

        Scene scene = new Scene(pane);
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

    public City findCity(String name, ArrayList<City> cities){
        for (int i = 0; i < cities.size(); i ++){
            if(cities.get(i).getCityName().equalsIgnoreCase(name)){
                return cities.get(i);
            }
        }
        return null;//if not found
    }
}