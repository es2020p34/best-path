package pt.ua.deti.es.p34.statistics;

import java.util.ArrayList;

public class Location {

    public String location;
    public ArrayList<Item> it;
    public String street;
    public Integer nCars;

    public Location(String location){
        this.location = location;
        this.it = new ArrayList<Item>();
    }
}