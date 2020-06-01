package pt.ua.deti.es.p34.controller;

import pt.ua.deti.es.p34.utils.Backend;
import pt.ua.deti.es.p34.utils.GeoDB;
import pt.ua.deti.es.p34.utils.Utils;
import pt.ua.deti.es.p34.utils.GeoDB.Event;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final Backend backend;
    private final GeoDB geoDB;

    @Autowired
    public ApiController(final Backend backend, final GeoDB geoDB) {
        this.backend = backend;
        this.geoDB = geoDB;
    }

    @GetMapping("/directions")
    public Map<String, Object> directions(@RequestParam(name = "lat0") double lat0,
            @RequestParam(name = "lon0") double lon0, @RequestParam(name = "lat1") double lat1,
            @RequestParam(name = "lon1") double lon1) {
        return backend.directions(lat0, lon0, lat1, lon1);
    }

    @GetMapping("/geocode")
    public Map<String, Object> geocode(@RequestParam(name = "text") String text) {
        return backend.geocode(text);
    }

    @GetMapping("/reverse")
    public Map<String, Object> reverse(@RequestParam(name = "lat") double lat, @RequestParam(name = "lon") double lon) {
        return backend.reverse(lat, lon);
    }

    @GetMapping("/speedlimit")
    public Map<String, Object> speedlimit(@RequestParam(name = "lat") double lat,
            @RequestParam(name = "lon") double lon) {
        return backend.speedlimit(lat, lon);
    }

    @GetMapping("/avgspeed")
    public Map<String, Object> avgspeed(@RequestParam(name = "lat") double lat,
            @RequestParam(name = "lon") double lon) {
        double avg_speed = geoDB.getAvgSpeed(lat, lon);

        if (avg_speed < 0) {
            Map<String, Object> reply = backend.speedlimit(lat, lon);
            avg_speed = Utils.cast(reply.get("speed_limit"));
        }

        Map<String, Object> reply = new HashMap<>();
        reply.put("avg_speed", avg_speed);

        return reply;
    }

    @GetMapping("/last")
    public Event lastEvent() {
        Event e = geoDB.getLastEvent();
        return e;
    }

    @PostMapping("/event")
    public Event newEmployee(@RequestBody Event event) {
        geoDB.insert_event(event);
        return event;
    }
}