package pt.ua.deti.es.p34.stepdefs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class TestsImplementation3 {

    private WebDriver driver = null;

    @Given("^the necessity to find the best path to the destination$")
    public void the_necessity_to_find_the_best_path_to_the_destination()  {
        driver = new HtmlUnitDriver();
    }

    @When("^I access the web application$")
    public void i_access_the_web_application() {
        driver.navigate().to("http://localhost:8080/");
    }

    @Then("^I can visualize the information$")
    public void i_can_visualize_the_information() {
        if(driver.findElement(By.id("map")).isEnabled()) {
            System.out.println("Test 3 Pass");
        } else {
            System.out.println("Test 3 Fail");
        }
        driver.close();
    }
}
