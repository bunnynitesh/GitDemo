package stepDefinations;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException
	{
		stepDefinations m = new stepDefinations();
		
		if(stepDefinations.place_id==null)
		{
		m.add_place_payload_with("Nitesh", "French", "New York");
		m.users_calls_with_http_request("AddPlace", "Post");
		m.verify_place_id_created_maps_to_using("Nitesh", "GetPlaceAPI");
		}
	}

}
