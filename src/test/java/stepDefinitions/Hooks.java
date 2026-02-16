package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {

		StepDefinition sd = new StepDefinition();
		if (StepDefinition.placeId == null) {
			sd.add_place_payload_with("Sangeetha", "English", "123, Main Street, City");
			sd.user_calls_with_http_request("AddPlaceAPI", "POST");
			sd.verify_place_id_created_maps_to_using("Sangeetha", "getPlaceAPI");
			sd.delete_place_payload();
		}
	}
}
