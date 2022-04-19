package stepDefinations;

import static io.restassured.RestAssured.given;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

import Pojo.Location;
import Pojo.addPlace;
import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class stepDefinations extends Utils {
	
	 RequestSpecification res;
	 ResponseSpecification respec;
	 Response response;
	 static String place_id;

	 
	 TestDataBuild data = new TestDataBuild();
	 @Given("Add Place Payload with {string} {string} {string}")
	 public void add_place_payload_with(String name, String language, String address) throws IOException {
		
        
        res = given().spec(requestSpecification()).body(data.addPlacePayload(name,language,address));
	}
	 @When("users calls {string} with {string} http request")
	public void users_calls_with_http_request(String resource, String method) {
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		
		
		respec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(method.equalsIgnoreCase("Post"))
		response = res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("Get"))
			response = res.when().post(resourceAPI.getResource());
		
	}
	
	@Then("API call is success with status code {int}")
	public void api_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String Expectedvalue) {
	    // Write code here that turns the phrase above into concrete actions    
	    assertEquals(getJsonPath(response, keyValue), Expectedvalue);
	}
	
	@Then("verify place_id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		users_calls_with_http_request(resource, "Get");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
		
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}


}
