package testCases;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.testBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Random_Generator_Utils;

public class Delete extends testBase {

	RequestSpecification httpRequest;
	Response response;
	
	String empName = Random_Generator_Utils.subdomain();
	String empSalary = Random_Generator_Utils.subdomain();
	String empAge = Random_Generator_Utils.subdomain();
	
	
	@BeforeClass
	void createEmployee() throws InterruptedException
	{
		RestAssured.baseURI = "http://dummy.restapiexample.com/api/v1";
		httpRequest = RestAssured.given();
		
		response = httpRequest.request(Method.GET, "/employees");
		
		JsonPath jsonPathEvauator = response.jsonPath();
		
		String empID = jsonPathEvauator.get("[0].id");
		response = httpRequest.request(Method.DELETE, "/delete/"+empID);
	}
	
	@Test
	void ckeckResposeBody()
	{
		String responseBody = response.getBody().asString();
		Assert.assertEquals(responseBody.contains("successfully! deleted Records"), true);
	}
	
	
}
