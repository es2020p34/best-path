package pt.ua.deti.es.p34.stepdefs;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import pt.ua.deti.es.p34.utils.Http;

import java.util.HashMap;
import java.util.Map;

public class TestsImplementation0 {

    private double lon0, lat0, lon1, lat1;

    @Given("^I am at Mercado do Bolhão$")
    public void i_am_at_Mercado_do_bolhão() throws Exception {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "mercado do bolhão");
        Map<String, Object> location = Http.getJson("http://localhost:8080/api/geocode", parameters);

        JSONObject loc0 = new JSONObject(location);
        JSONObject feat0 = loc0.getJSONArray("features").getJSONObject(0);
        lon0 = (Double) feat0.getJSONObject("geometry").getJSONArray("coordinates").get(0);
        lat0 = (Double) feat0.getJSONObject("geometry").getJSONArray("coordinates").get(1);
    }

    @When("^I search for the fastest path to Foz do Douro$")
    public void i_search_for_the_fastest_path_to_Foz_do_Douro() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "foz do douro");
        Map<String, Object> location = Http.getJson("http://localhost:8080/api/geocode", parameters);
        
        JSONObject loc1 = new JSONObject(location);
        JSONObject feat1 = loc1.getJSONArray("features").getJSONObject(0);
        lon1 = (Double) feat1.getJSONObject("geometry").getJSONArray("coordinates").get(0);
        lat1 = (Double) feat1.getJSONObject("geometry").getJSONArray("coordinates").get(1);
    }

    @Then("^I find the road with less congestion$")
    public void i_find_the_road_with_less_congestion() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat0", Double.toString(lat0));
        parameters.put("lon0", Double.toString(lon0));
        parameters.put("lat1", Double.toString(lat1));
        parameters.put("lon1", Double.toString(lon1));
        Map<String, Object> path = Http.getJson("http://localhost:8080/api/directions", parameters);
        
        JSONObject fastpath = new JSONObject(path);
        JSONArray array = fastpath.getJSONArray("features");

        if(array.length() != 0) {
            System.out.println("Test 0 Pass");
        } else {
            System.out.println("Test 0 Fail");
        }
    }
}