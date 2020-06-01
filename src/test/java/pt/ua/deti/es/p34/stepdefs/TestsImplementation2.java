package pt.ua.deti.es.p34.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.JSONObject;
import pt.ua.deti.es.p34.utils.Http;

import java.util.HashMap;
import java.util.Map;

public class TestsImplementation2 {

    private double lat, lon, avg, lmt;

    @Given("^several buses on Porto$")
    public void several_buses_on_Porto() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^is necessary the information about traffic to process the data$")
    public void is_necessary_the_information_about_traffic_to_process_the_data() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^use the velocity of these buses to know if the traffic is slow, fast or normal\\.$")
    public void use_the_velocity_of_these_buses_to_know_if_the_traffic_is_slow_fast_or_normal() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^Mousinha da Silveira street$")
    public void Mousinho_da_Silveira_street() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("text", "Rua Mousinho da Silveira");
        Map<String, Object> location = Http.getJson("http://localhost:8080/api/geocode", parameters);
        JSONObject loc = new JSONObject(location);
        JSONObject feat = loc.getJSONArray("features").getJSONObject(0);
        lon = (Double) feat.getJSONObject("geometry").getJSONArray("coordinates").get(0);
        lat = (Double) feat.getJSONObject("geometry").getJSONArray("coordinates").get(1);
    }


    @Then("^we compare the values of limits and the median velocity$")
    public void we_compare_the_values_of_limits_and_the_median_velocity() throws Exception {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("lat", Double.toString(lat));
        parameters.put("lon", Double.toString(lon));
        Map<String, Object> avg_J = Http.getJson("http://localhost:8080/api/avgspeed", parameters);
        avg = (Double) avg_J.get("avg_speed");
        parameters = new HashMap<>();
        parameters.put("lat", Double.toString(lat));
        parameters.put("lon", Double.toString(lon));
        Map<String, Object> lmt_J = Http.getJson("http://localhost:8080/api/speedlimit", parameters);
        try {
            lmt = (Double) lmt_J.get("avg_speed");
        } catch(Exception e) {
            lmt = 50;
        }
    }

    @When("^we know if that street is congestion or not$")
    public void we_know_if_that_street_is_congestion_or_not() throws Exception {

        if (avg <= (1/4)*lmt) {
            System.out.println("transit high");
        } else if (avg <= (1/2)*lmt) {
            System.out.println("transit moderate");
        } else {
            System.out.println("transit low");
        }
    }
}
