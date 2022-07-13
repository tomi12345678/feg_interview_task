package base;


import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class testBase {
	
	//objects specific to all test cases
	public static RequestSpecification httpRequest;
	public static Response response;
	public String id = "cl2pxld6h45000iln8lwv81ai3"; 
	public String pageId = "cl2pxld6h45000iln8lwv81ai3";
	public String name = "Test Component";
	public String description = "Test Description";
	public String status = "OPERATIONAL";
	public String component_id = "cl5i7g7nr70992wlorva70bkbv";
	public String group = "Test Group";
	public String incident_id = "cl5jloz5j1213385vop5oferx95";
	public String incident_name = "incident name 123";
	public String incident_message = "Testing the incident";
	public String incident_status = "Statusing";
	
	

	
	@BeforeClass
	public void setup()
	{
		String bearerToken = "8d16333936e72e705980878e18c95976";
		RestAssured.authentication = RestAssured.oauth2(bearerToken);
		
		
	}
}
