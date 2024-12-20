@ChataAPI
Feature: GET API for newly created Employees
 This feature file should cover the test cases for GET API Call
 
Background:
 * def headersFile = read("classpath:headers.json")
 * configure headers = headersFile
Given url 'https://jsonplaceholder.typicode.com'
And print "==BackGround Run=="


Scenario: To get employee deatils based on ID
Given path '/users'
When method get
Then status 200
And print response
And match response contains deep {"name":"Nicholas Runolfsdottir V"}



