package pt.ua.deti.es.p34.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class Backend {

    private static final Logger LOG = LoggerFactory.getLogger(Backend.class);

    static final String directionUrl = "https://api.openrouteservice.org/v2/directions/driving-car";
    static final String geocodeUrl = "https://api.openrouteservice.org/geocode/search";
    static final String reverseUrl = "https://api.openrouteservice.org/geocode/reverse";
    static final String speedLimitUrl = "http://overpass-api.de/api/interpreter";
    static final String pathDesignUrl = "https://api.mapbox.com/matching/v5/mapbox/driving/";

    private final String api_key;
    private final String mapbox_api_key;

    @Autowired
    public Backend(@Value("${api.key}") String api_key, @Value("${mapbox.api.key}") String mapbox_api_key) {
        this.api_key = api_key;
        this.mapbox_api_key = mapbox_api_key;
    }

    @Cacheable("pathdesign")
    public Map<String, Object> pathdesign(String coords, String radius) {
        Map<String, String> params = new HashMap<>();
        params.put("access_token", mapbox_api_key);
        params.put("radiuses", radius);
        params.put("geometries", "geojson");
        params.put("steps", "true");

        Map<String, Object> reply = null;

        try {
            reply = Http.getJson(pathDesignUrl + coords, null, params);
        } catch (UnsupportedEncodingException e) {
            LOG.error("pathdesign", e);
        }

        return reply;
    }

    @Cacheable("directions")
    public Map<String, Object> directions(double lat0, double lon0, double lat1, double lon1) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("start", lon0 + "," + lat0);
        params.put("end", lon1 + "," + lat1);

        LOG.info("directions", params);

        Map<String, Object> reply = null;

        try {
            reply = Http.getJson(directionUrl, null, params);
        } catch (UnsupportedEncodingException e) {
            LOG.error("directions", e);
        }

        return reply;
    }

    @Cacheable("geocode")
    public Map<String, Object> geocode(String text) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("text", text);

        Map<String, Object> reply = null;

        try {
            reply = Http.getJson(geocodeUrl, null, params);
        } catch (UnsupportedEncodingException e) {
            LOG.error("geocode", e);
        }

        return reply;
    }

    @Cacheable("reverse")
    public Map<String, Object> reverse(double lat, double lon) {
        Map<String, String> params = new HashMap<>();
        params.put("api_key", api_key);
        params.put("point.lat", Double.toString(lat));
        params.put("point.lon", Double.toString(lon));

        Map<String, Object> reply = null;

        try {
            reply = Http.getJson(reverseUrl, null, params);
        } catch (UnsupportedEncodingException e) {
            LOG.error("reverse", e);
        }

        return reply;
    }

    @Cacheable("speedlimit")
    public Map<String, Object> speedlimit(double lat, double lon) {
        double speed_limit = 0;

        Map<String, String> params = new HashMap<>();
        params.put("data", "[out:json];way(around:1000," + lat + "," + lon + ")[\"maxspeed\"];out;");

        try {
            Map<String, Object> reply = Http.getJson(speedLimitUrl, null, params);
            List<Object> elements = Utils.cast(reply.get("elements"));

            if (elements.size() > 0) {
                Map<String, Object> element = Utils.cast(elements.get(0));
                Map<String, Object> tags = Utils.cast(element.get("tags"));
                speed_limit = Double.parseDouble((String) tags.get("maxspeed"));
            } else {
                speed_limit = 0;
            }

        } catch (UnsupportedEncodingException e) {
            LOG.error("speedlimit", e);
        }

        Map<String, Object> reply = new HashMap<>();
        reply.put("speed_limit", speed_limit);

        return reply;
    }
}