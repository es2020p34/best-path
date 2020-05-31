package pt.ua.deti.es.p34.statistics;

import java.time.LocalDateTime;

public class Item {

    public String lon;
    public String lat;
    
    public String vehicle;
    public LocalDateTime hora;

    public Item(String lon, String lat, String vehicle) {
        this.lon = lon;
        this.lat = lat;
        this.vehicle = vehicle;
        //LocalDateTime hora = LocalDateTime.parse(dhora, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
        //hora.getMonth() 
        //outra funcao..
        //LocalDateTime toDateTime = LocalDateTime.of(2014, 9, 10, 6, 40, 45);
        //LocalDateTime tdate = LocalDateTime.from(hora);
        //tdate.until(toDateTime, ChronoUnit.DAYS);
    }
}