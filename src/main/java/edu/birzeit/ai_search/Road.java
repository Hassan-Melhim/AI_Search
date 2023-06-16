package edu.birzeit.ai_search;

public class Road {//Edge

    private City city1;//point A
    private City city2;//
    private double g;//actual cost of edge
    private double h;//heuristic cost of edge


    public Road(City city1, City city2, double g, double h) {
        this.city1 = city1;
        this.city2 = city2;
        this.g = g;
        this.h = h;
    }

    public City getCity1() {
        return city1;
    }

    public void setCity1(City city1) {
        this.city1 = city1;
    }

    public City getCity2() {
        return city2;
    }

    public void setCity2(City city2) {
        this.city2 = city2;
    }

    public double getActualDistance() {
        return g;
    }

    public void setActualDistance(double distance){
        this.g = distance;
    }

    public double getHeuristicDistance(){
        return this.h;
    }

    public void setHeuristicDistance(double distance) {
        this.h = distance;
    }
}
