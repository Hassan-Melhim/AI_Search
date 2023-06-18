package edu.birzeit.ai_search;

public class City implements Comparable<City>{//Vertex
    private double x;
    private double y;
    private String name;
    private City parent;//
    double f;//function. f = g+h
    double travelledCost;//score of the cumulated actual distance

    public City(double x, double y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public City(){
        this.x = 0;
        this.y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getCityName() {
        return name;
    }

    public void setCityName(String name) {
        this.name = name;
    }

    public City getParent() {
        return parent;
    }

    public void setParent(City parent) {
        this.parent = parent;
    }

    @Override
    public int compareTo(City c) {
        if(this.f - c.f > 0)
            return 1;
        if(this.f - c.f < 0)
            return -1;
        return 0;
    }
}
