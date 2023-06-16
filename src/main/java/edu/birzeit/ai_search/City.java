package edu.birzeit.ai_search;

public class City implements Comparable<City>{//Vertex
    private int x;
    private int y;
    private String name;
    private City parent;//
    double f;//function. f = g+h
    double travelledCost;//score of the cumulated actual distance

    public City(int x, int y, String name){
        this.x = x;
        this.y = y;
        this.name = name;
    }

    public City(){
        this.x = 0;
        this.y = 0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
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
