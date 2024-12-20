@ChataAPI 
Feature: DELETE API for Employees Record Deletion
 This feature file should cover the test cases for Delete API Call  
 
Background:
 * def headersFile = read("classpath:headers.json")
 * configure headers = headersFile
Given url 'https://jsonplaceholder.typicode.com'

Scenario Outline: To get employee deatils based on ID
Given path '/users/<id>'
When method delete
Then status 200
And print response
Examples:
|id|
|1|