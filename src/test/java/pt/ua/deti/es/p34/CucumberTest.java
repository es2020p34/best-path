package pt.ua.deti.es.p34;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"src/test/resource/features"}, plugin = {"pretty", "json:target/cucumber-report.json"})
public class CucumberTest {
}