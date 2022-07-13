package testCases;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.testBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utilities.Random_Generator_Utils;

public class v1_page_id_incidents extends testBase {
	

	
	@BeforeClass
	void createEmployee() throws InterruptedException
	{
		httpRequest = RestAssured.given()
		.baseUri("https://api.instatus.com/v1/")
		.basePath("{page_id}")
		.pathParam("page_id", pageId);
		response = httpRequest.request(Method.GET, "/incidents");
	}
	
	@Test
	void getIncidentsList()
	{
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		assertEquals(responseBody.contains("new incident"), true);
		assertEquals(responseBody.contains("New test incident1"), true);
	}

	@Test
	void getIncidentsSingleRecord()
	{
		httpRequest = RestAssured.given()
		.basePath("{page_id}/{incident_id}")
		.pathParams("page_id", pageId, "incident_id", incident_id);
		response = httpRequest.request(Method.GET, "https://api.instatus.com/v1/{page_id}/incidents/{incident_id}");
		
		String responseBody = response.getBody().asString();
		assertEquals(responseBody.contains("cl5ia0lqd61240brn3u8tczbuk"), false);
	}	
}
