package pt.ua.deti.es.p34.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestsImplementation1 {

    private WebDriver driver = null;

    @Given("^I want to know more information about road congestion$")
    public void i_want_to_know_more_information_about_road_congestion() {
        driver = new HtmlUnitDriver();
    }

    @When("^I see the statistics page on BestPath system$")
    public void i_see_the_statistics_page_on_BestPath_system() {
        driver.get("http://localhost:8080/statistics");
    }

    @Then("^I obtain detailed information about general road congestion$")
    public void i_obtain_detailed_information_about_general_road_congestion() {
        if(driver.findElement(By.id("container")).isEnabled()) {
            System.out.println("Test 1 Pass");
        } else {
            System.out.println("Test 1 Fail");
        }
        driver.close();
    }

    @Given("^a person with interesting to study the road congestion$")
    public void a_person_with_interesting_to_study_the_road_congestion() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^using the information available on the system$")
    public void using_the_information_available_on_the_system() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the person can to make a dataset\\.$")
    public void the_person_can_to_make_a_dataset() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^data information about all the roads congestion$")
    public void data_information_about_all_the_roads_congestion() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @When("^official people want to improve the quality of traffic$")
    public void official_people_want_to_improve_the_quality_of_traffic() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^they use the system to visualize the behaviour on last times\\.$")
    public void they_use_the_system_to_visualize_the_behaviour_on_last_times() throws Exception {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
