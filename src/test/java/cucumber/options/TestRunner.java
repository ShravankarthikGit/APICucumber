package cucumber.options;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/java/features",
    glue = "stepDefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"},
    monochrome = true,
    //tags = "not @ignore"  // The 'not' keyword skips the tagged scenario
    tags = "@DeletePlace"  // Run scenarios tagged with either @addPlace, @getPlace, or @deletePlace
)
public class TestRunner {
}
