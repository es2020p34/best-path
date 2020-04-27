package pt.ua.deti.es.p34;

public class Item {

    public String lon;
    public String lat;
    
    public String vehicle;
    public String hora; //...

    public Item(String lon, String lat, String vehicle){
        this.lon = lon;
        this.lat = lat;
        this.vehicle = vehicle;
    }
}