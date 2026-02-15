Feature: Validating PLace API's

# In here we have AddPlaceAPI passed as parameter in step definition and we can reuse the same step definition for other
# API's as well by just changing the parameter value in feature file

Scenario: Verify if place is being added using add place api
	Given Add Place payload
	When user calls "AddPlaceAPI" with "Post" http request
	Then the response is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	
# Scenarion with data driven testing using Scenario Outline and Examples table	
Scenario Outline: Verify if place is being added using add place api using data from test
	Given Add Place payload with "<name>" "<language>" "<address>"
	When user calls "AddPlaceAPI" with "Post" http request
	Then the response is success with status code 200
	And "status" in response body is "OK"
	And "scope" in response body is "APP"
	
Examples:
	| name | language | address 
	| Sangeetha | English | 123, Main Street, City
	| John Doe | Spanish | 456, Elm Street, Town
	| Jane Smith | French | 789, Oak Street, Village