package pt.ua.deti.es.p34.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONArray;
import org.json.JSONObject;
import pt.ua.deti.es.p34.utils.Http;

import java.util.HashMap;
import java.util.Map;

public class TestsImplementation0 {
    @Given("^I am at Mercado do Bolhão$")
    public void i_am_at_Mercado_do_bolhão() throws Exception {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "mercado do bolhão");
        Map<String, Object> location = Http.getJson("http://localhost:8080/api/geocode", parameters);

        JSONObject loc0 = new JSONObject(location);
        JSONObject feat0 = loc0.getJSONArray("features").getJSONObject(0);
        double lon = (Double) feat0.getJSONObject("geometry").getJSONArray("coordinates").get(0),
        lat = (Double) feat0.getJSONObject("geometry").getJSONArray("coordinates").get(1);
    }

    @When("^I search for the fastest path to Foz do Douro$")
    public void i_search_for_the_fastest_path_to_Foz_do_Douro() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "foz do douro");
        Map<String, Object> location = Http.getJson("http://localhost:8080/api/geocode", parameters);
        
        JSONObject loc1 = new JSONObject(location);
        JSONObject feat1 = loc1.getJSONArray("features").getJSONObject(0);
        double lon = (Double) feat1.getJSONObject("geometry").getJSONArray("coordinates").get(0),
        lat = (Double) feat1.getJSONObject("geometry").getJSONArray("coordinates").get(1);
    }

    @Then("^I find the road with less congestion$")
    public void i_find_the_road_with_less_congestion() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat0", "40.635013");
        parameters.put("lon0", "-8.651136");
        parameters.put("lat1", "40.643384");
        parameters.put("lon1", "-8.6427427");
        Map<String, Object> path = Http.getJson("http://localhost:8080/api/directions", parameters);
        
        JSONObject fastpath = new JSONObject(path);
        JSONArray array = fastpath.getJSONArray("features");
    }

    @Given("^I need instant information about road congestion$")
    public void i_need_instant_information_about_road_congestion() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^I look to the Porto map$")
    public void i_look_to_the_Porto_map() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^I regard the principal information to avoid some roads$")
    public void i_regard_the_principal_information_to_avoid_some_roads() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}