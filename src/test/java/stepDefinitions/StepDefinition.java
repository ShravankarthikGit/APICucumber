package stepDefinitions;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.Location;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.RestAssured.*;

public class StepDefinition extends Utils {
	RequestSpecification requestSpec;
	ResponseSpecification resSpec;
	Response response;
	TestDataBuild data = new TestDataBuild();

	@Given("Add Place payload")
	public void add_place_payload() throws IOException {
		Location RequestBody = data.addPlacePayLoad();
		requestSpec = given().spec(Utils.requestSpec()).body(RequestBody);

	}
	
	@Given("Add Place payload with {string} {string} {string}")
	public void add_place_payload_with(String string, String string2, String string3) throws IOException {
		Location RequestBody = data.addPlacePayLoad();
		RequestBody.setName(string);
		RequestBody.setLanguage(string2);
		RequestBody.setAddress(string3);
		requestSpec = given().spec(Utils.requestSpec()).body(RequestBody);
	}



	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String string, String string2) {
		
		response = requestSpec.when().post("/maps/api/place/add/json");
	}

	@Then("the response is success with status code {int}")
	public void the_response_is_success_with_status_code(Integer int1) {
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		response.then().spec(resSpec);
		response.prettyPrint();
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
		String resp = response.asString();

		JsonPath js = new JsonPath(resp);
		String actualValue = js.getString(key);
		assertEquals(expectedValue, actualValue);
	}

}
