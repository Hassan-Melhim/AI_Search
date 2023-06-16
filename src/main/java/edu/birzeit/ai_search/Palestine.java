package edu.birzeit.ai_search;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Palestine {//Undirected Graph

    private ArrayList<City> cities;
    private ArrayList<Road> roads;

    public Palestine(ArrayList<City> cities, ArrayList<Road> roads) {
        this.cities = cities;
        this.roads = roads;
    }

    private double findActualDistance(City source, City destination){
        //because it is an undirected graph, an edge could be saved in two forms
        for(int i = 0; i <roads.size(); i++){
            //form 1: city1,city2:source,destination:
            if(roads.get(i).getCity1().equals(source)){
                if(roads.get(i).getCity2().equals(destination)){
                    return roads.get(i).getActualDistance();
                }
            }
            //form 2: city1,city2:destination,city
            if(roads.get(i).getCity1().equals(destination)){
                if(roads.get(i).getCity2().equals(source)){
                    return roads.get(i).getActualDistance();
                }
            }
        }
        return -1;//if the edge is not found return negative distance
    }

    private double findHeuristicDistance(City source, City destination){
        //because it is an undirected graph, an edge could be saved in two forms
        for(int i = 0; i < roads.size(); i++){
            //form 1: city1,city2:source,destination:
            if(roads.get(i).getCity1().equals(source)){
                if(roads.get(i).getCity2().equals(destination)){
                    return roads.get(i).getHeuristicDistance();
                }
            }
            //form 2: city1,city2:destination,city
            if(roads.get(i).getCity1().equals(destination)){
                if(roads.get(i).getCity2().equals(source)){
                    return roads.get(i).getHeuristicDistance();
                }
            }
        }
        return -1;//if the edge is not found return negative distance
    }

    private ArrayList<City> findAdjacentCities (City current){
        ArrayList<City> adj = new ArrayList<>();

        for(int i = 0; i < roads.size(); i++){

            if(roads.get(i).getCity1().equals(current.getCityName())){
                adj.add(roads.get(i).getCity2());
            }//if the current is the source city, save the destination city

            if(roads.get(i).getCity2().equals(current.getCityName())){
                adj.add(roads.get(i).getCity1());
            }//if the current is the destination city, save the source city
        }

        return null;//if no adjacent is found
    }

    public City AStar (City start, City end){
        PriorityQueue<City> unvisited = new PriorityQueue<>();
        PriorityQueue<City> visited = new PriorityQueue<>();

        start.f = 0;//since no traveling has been made the distance traveled is 0
        start.travelledCost=0;//since no traveling has been made the distance traveled is 0
        unvisited.add(start);

        while(!unvisited.isEmpty()){
            City current = unvisited.peek();

            if(visited.contains(current)){//if the current city has been visited
                unvisited.remove(current);//remove it from the unvisited list
                current = unvisited.peek();//pick a new current city
            }

            if(current == end){//end statement
                return current;
            }


            ArrayList<City> available = findAdjacentCities(current);//get all the adjacent cities of the current city
            for(int i = 0; i < available.size(); i ++){//walk through them to calculate the f, then adding them to the unvisited queue
                available.get(i).travelledCost = findActualDistance(current,available.get(i)) + current.travelledCost;
                available.get(i).f = available.get(i).travelledCost + findHeuristicDistance(current, available.get(i));
                unvisited.add(available.get(i));
                available.get(i).setParent(current);//in order to remember the path taken
            }

            unvisited.remove(current);
            visited.add(current);
        }

        return null;//return -1 if not found
    }

    public City greedySearch(City start, City end){
        PriorityQueue<City> unvisited = new PriorityQueue<>();
        PriorityQueue<City> visited = new PriorityQueue<>();

        start.f = 0;//since no traveling has been made the distance traveled is 0
        unvisited.add(start);

        while(!unvisited.isEmpty()) {
            City current = unvisited.peek();

            if (visited.contains(current)) {//if the current city has been visited
                unvisited.remove(current);//remove it from the unvisited list
                current = unvisited.peek();//pick a new current city
            }

            if (current == end) {//end statement
                return current;
            }


            ArrayList<City> available = findAdjacentCities(current);//get all the adjacent cities of the current city
            for (int i = 0; i < available.size(); i++) {//walk through them to calculate the f, then adding them to the unvisited queue
                available.get(i).f = findActualDistance(current, available.get(i)) + current.f;
                unvisited.add(available.get(i));
                available.get(i).setParent(current);//in order to remember the path taken
            }

            unvisited.remove(current);
            visited.add(current);
        }
        return null;//if not found
    }
}
