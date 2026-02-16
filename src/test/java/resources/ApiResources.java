package resources;

// enumeration to store all the API resources. Is a special class that represents a group of constants (unchangeable variables, like final variables).
public enum ApiResources {

	AddPlaceAPI("/maps/api/place/add/json"), getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json");
	
	private String res;

	ApiResources(String resource) {
		res = resource;
	}
	
	
	public String getResource() {
		return this.res;
	}

}
