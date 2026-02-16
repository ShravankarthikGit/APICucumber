package resources;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import pojo.Coordinates;
import pojo.Location;

public class TestDataBuild {

	public Location addPlacePayLoad() {

		// Create object of Location class and set values to its variables using setter
		// methods
		Location Request = new Location();
		Request.setAccuracy(50);
		Request.setAddress("29, side layout, cohen 09");
		Request.setLanguage("French-IN");
		Request.setName("Frontline house");
		Request.setPhone_number("(+91) 983 893 3937");
		Request.setWebsite("http://google.com");
		Request.setTypes(List.of("shoe park", "shop"));

		Coordinates coordinates = new Coordinates();
		coordinates.setLat("-38.383494");
		coordinates.setLng("33.427362");

		Request.setLocation(coordinates);

		return Request;
	}
	
	
	public ObjectNode deletePlacePayLoad(String placeId) {
		// 1. Initialize the mapper (the "factory")
		ObjectMapper mapper = new ObjectMapper();

		// 2. Create the JSON object (ObjectNode)
		ObjectNode payload = mapper.createObjectNode();
		
		payload.put("place_id", placeId);
		return  payload;
	}

}
