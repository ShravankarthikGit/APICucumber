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
import resources.ApiResources;
import resources.TestDataBuild;
import resources.Utils;
import static io.restassured.RestAssured.*;

public class StepDefinition extends Utils {
	RequestSpecification requestSpec;
	ResponseSpecification resSpec;
	static Response response;
	TestDataBuild data = new TestDataBuild();
	// Declare placeId as a static variable to store the place_id across different steps in different scenarios
	static String placeId; 
	JsonPath js ;

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
	public void user_calls_with_http_request(String resource, String httpmethod) {
		// constructor will be called with value of resource which you pass from the ENUM class "APIRespurces" to get the resource
		ApiResources resourceAPI = ApiResources.valueOf(resource);
		if (httpmethod.equalsIgnoreCase("POST"))
			// using getResource() method to get the resource from the ENUM class
			response = requestSpec.when().post(resourceAPI.getResource());
		else if (httpmethod.equalsIgnoreCase("GET"))
			response = requestSpec.when().get(resourceAPI.getResource());
		else if (httpmethod.equalsIgnoreCase("DELETE"))
			response = requestSpec.when().delete(resourceAPI.getResource());
	}

	@Then("the response is success with status code {int}")
	public void the_response_is_success_with_status_code(Integer int1) {
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		response.then().spec(resSpec);
		response.prettyPrint();
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String expectedValue) {
		String actualValue = Utils.getValuefromJson( response, key);
		assertEquals(expectedValue, actualValue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String name, String Resource) throws IOException {
		// Get place_id from the previous response and use it to verify the place_id created maps to the name using a GET API call
		placeId = Utils.getValuefromJson( response, "place_id");
		System.out.println("placeId: " + placeId);
		// Get API call 
		requestSpec = given().spec(Utils.requestSpec()).queryParam("place_id", placeId);
		user_calls_with_http_request(Resource, "GET");
		// validate that the name in the response is the same as the name used in the Add Place API request
		the_response_is_success_with_status_code(200);
		
		String actualValue = Utils.getValuefromJson( response, "name");
		assertEquals(name, actualValue);
	}
	
	@Given("Delete Place payload")
	public void delete_place_payload() throws IOException {
		requestSpec = given().spec(Utils.requestSpec()).body(data.deletePlacePayLoad(placeId));
	}
}
