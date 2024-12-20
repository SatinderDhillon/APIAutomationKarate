Feature: To create employee records
 POST Call for creating the Employee Records 
 
 Background: To create the Base URL 
 Given url 'http://dummy.restapiexample.com/api/v1/create'
 
 Scenario: To create employee record
 Given request {"name":"WRTEST","salary":"12323","age":"23"}
 And headers {Accept: 'application/json', Content-Type: 'application/json'}
 When method post
 And status 200
 And match response.id == "2"
 
 Scenario: To create employee record
 * def reqBody = read("data/empDetails.json")
 Given request reqBody
 And headers {Accept: 'application/json', Content-Type: 'application/json'}
 When method post
 And status 200
 And match response.id == "2"