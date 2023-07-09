package edu.birzeit.ai_search;
//Zaina Amous and Hassan Melhim

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Driver extends Application {

    public static Control control;
    ArrayList<City> path;
    Palestine graph;
    int click = 0;//to know whether city clicked is source or destination

    @Override
    public void start(Stage stage) throws IOException {
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
                        Double.parseDouble(row[2]), 5);
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

                City city1 = findCity(airRow[0], cityTemp);
                City city2 = findCity(airRow[1], cityTemp);

                roadTemp.add(new Road(city1, city2
                        , Double.parseDouble(actualRow[2]), Double.parseDouble(airRow[2])));
                ;

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            actualReader.close();
            airReader.close();
        }
        this.graph = new Palestine(cityTemp,roadTemp);
        //////////////////////////////////////////////////////////////////////////
        BorderPane pane = new BorderPane();
        //right
        VBox right = new VBox();
        right.setPadding(new Insets(10));

        TextField source = new TextField();
        source.setPromptText("Source");

        TextField destination = new TextField();
        destination.setPromptText("Destination");

        HBox search = new HBox();
        search.setPadding(new Insets(5));
        Button aSearch = new Button("Search with A*");
        Button gSearch = new Button("Search with greedy");
        search.getChildren().addAll(aSearch,gSearch);

        Button clear = new Button("Clear");

        TextArea pathArea = new TextArea();

        right.getChildren().addAll(source, destination,search, clear, pathArea);
        pane.setRight(right);

        //center
        FileInputStream input = new FileInputStream("src\\main\\resources\\PalestineMap.jpeg");
        Image image = new Image(input);
        ImageView mv = new ImageView(image);
        mv.setFitHeight(300);
        mv.setFitWidth(300);
        pane.setCenter(mv);

        //////////////////////////////////////////////////////////////////////////
        //generate dots
        for (int i = 0; i < graph.getCities().size(); i++) {
            graph.getCities().get(i).c.setFill(Color.RED);
            pane.getChildren().add(graph.getCities().get(i).c);
        }
        pane.addEventHandler(MouseEvent.ANY, event -> {
            if(event.getEventType() == MouseEvent.MOUSE_PRESSED){
                for(int i= 0; i < graph.getCities().size(); i++){
                    if (graph.getCities().get(i).getX() + 10 > event.getX() && graph.getCities().get(i).getX() - 10 < event.getX()
                            && graph.getCities().get(i).getY() + 10 > event.getY() && graph.getCities().get(i).getY() - 10 < event.getY()) {

                        if (graph.getCities().get(i).c.equals(Color.RED)) {
                            click++;
                            if (click == 1) {
                                graph.getCities().get(i).c.setFill(Color.GREEN);
                                source.setText(graph.getCities().get(i).getCityName());
                                System.out.println("sss");
                            }
                            if (click == 2) {
                                graph.getCities().get(i).c.setFill(Color.GREEN);
                                destination.setText(graph.getCities().get(i).getCityName());
                            }

                        }
                        break;
                    }
                }
            }
        });
        //////////////////////////////////////////////////////////////////////////
        //button events
        aSearch.setOnAction(actionEvent -> {
            City start = findCity(source.getText().toString(), this.graph.getCities());
            City end = findCity(destination.getText().toString(), this.graph.getCities());
            if(start == null || end == null){
                System.out.println("Input ERROR");
            }
            City result = this.graph.aStar(start, end);

            if(result == null ){
                pathArea.setText("No path exists");
            }
            else{
                ArrayList<String> paths = new ArrayList<String>();
                paths = calcPath(result);

                for(int i = 0; i < this.path.size(); i++){
                    pathArea.setText(pathArea.getText() + "\n" + paths.get(i) + "-->" + "\n");
                }
            }
        });

        gSearch.setOnAction(actionEvent -> {
            City start = findCity(source.getText().toString(), this.graph.getCities());
            City end = findCity(destination.getText().toString(), this.graph.getCities());
            if(start == null || end == null){
                System.out.println("ss");
            }
            City result = this.graph.greedySearch(start, end);
            if(result == null ){
                pathArea.setText("No path exists");
            }
            else {
                //this.path = calcPath(result);

                for (int i = 0; i < this.path.size(); i++) {
                    pathArea.setText(pathArea.getText() + this.path.get(i).getCityName() + "-->" + "\n");
                }
            }
        });


        Scene scene = new Scene(pane, 500, 500);
        stage.setTitle("Search Algorithms!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

//    public ArrayList<City> calcPath (City current){
//        ArrayList<City> path  = new ArrayList<City>();
//        int i = 0;
//        do {
//            path.add(current);
//            current = current.getParent();
//            System.out.println(path.size());
//            i++;
//        } while (current.getParent() != null || i<5);
//        Collections.reverse(path);
//        return path;
//    }

    public ArrayList<String> calcPath(City current){
        ArrayList<String> path  = new ArrayList<String>();

        do{
            path.add(current.getCityName());
            current = current.getParent();
        } while(current.getParent() != null);
        return path;
    }

    public City findCity(String name, ArrayList<City> cities){
        for (int i = 0; i < cities.size(); i ++){
            //System.out.println(cities.get(i).getCityName() + "\n name:" + name);
            if(cities.get(i).getCityName().equalsIgnoreCase(name)){
                return cities.get(i);
            }
        }
        return null;//if not found
    }
}