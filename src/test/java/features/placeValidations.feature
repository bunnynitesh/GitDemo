Feature: Validating Place API's

@AddPlace
Scenario Outline: Verify if place is successfully added using addPlace API
Given Add Place Payload with "<name>" "<language>" "<address>"
When users calls "AddPlaceAPI" with "Post" http request
Then API call is success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_id created maps to "<name>" using "GetPlaceAPI"

Examples:
	|name    |language |address          |
	|AHouse  |English  |A-102 Academy Rd |
	|BHouse  |Spanish  |A-105 Golf Cr Rd |
	
	
@DeletePlace	
Scenario: Verify if Delete Place functionality is working
	Given DeletePlace Payload
	When users calls "DeletePlaceAPI" with "Post" http request
	Then API call is success with status code 200
	And "status" in response body is "OK"