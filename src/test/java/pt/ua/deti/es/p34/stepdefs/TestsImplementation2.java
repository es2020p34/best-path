package pt.ua.deti.es.p34.stepdefs;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import pt.ua.deti.es.p34.statistics.Location;

public class TestsImplementation2 {

    private Location location;

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
        location = new Location("Rua Mousinho da Silveira");
    }

    @When("^we know if that street is congestion or not$")
    public void we_know_if_that_street_is_congestion_or_not() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^we compare the values of limits and the median velocity$")
    public void we_compare_the_values_of_limits_and_the_median_velocity() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
