package testCases;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
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

public class v1_page_id_components extends testBase {
	
	@BeforeClass
	void baseUri()
	{
		httpRequest = RestAssured.given()
		.baseUri("https://api.instatus.com/v1/")
		.basePath("{page_id}")
		.pathParam("page_id", pageId);
		response = httpRequest.request(Method.GET, "/components");
	}
	
	@Test
	void checkResponseBody()
	{
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		assertEquals(responseBody.contains("\"id\":\"cl2pxld7l45028iln8fmkpzy3z\""), true);
	}

	@Test
	void ckeckStatusCode()
	{
		int statusCode = response.getStatusCode();
		assertEquals(statusCode, 200);
	}
	
	@Test
	void checkResponseTime()
	{
		long responseTime = response.getTime();
		System.out.println("Reponse time for GET https://api.instatus.com/v1/:page_id/components is: " + responseTime);
		Assert.assertTrue(responseTime < 3000);
	}
	
	@Test
	void verifyConnectionStatus()
	{
		String connection = response.getHeader("Connection");
		Assert.assertEquals(connection, "close");	
		// When tested in postman, the header response value is "close" for the "Connection" header, but for this test, it finds "keep-alive"
	}
	
	@Test
	void verifyPostStatusCode()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("description", description);
		requestParams.put("status", status);

		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println("Status Code is: " + statusCode);
		assertEquals(responseBody.contains(name), true);
		assertEquals(responseBody.contains(description), true);
		assertEquals(responseBody.contains(status), true);
	}
	
	@Test
	void verifyPostRequiredFields()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", component_id);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 500);
		System.out.println("Status Code is: " + statusCode);
	}
	
	@Test
	void verifyPostIdIsUnique()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", component_id);
		requestParams.put("name", name);
		requestParams.put("description", description);
		requestParams.put("status", status);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		assertFalse(responseBody.contains(component_id));
	}
	
	@Test
	void verifyPostStatusCategoryIsPredefined()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", component_id);
		requestParams.put("name", name);
		requestParams.put("description", description);
		requestParams.put("status", "INTERCHANGABLE");
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		int statusCode = response.getStatusCode();
		System.out.println("Status code is: " + statusCode);
		assertEquals(statusCode, 500);		
	}
	
	@Test
	void verifyPostGrouping()
	{
		JSONObject requestParams = new JSONObject();
		requestParams.put("id", component_id);
		requestParams.put("name", name);
		requestParams.put("description", description);
		requestParams.put("status", status);
		requestParams.put("grouped", true);
		requestParams.put("group", group);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		assertFalse(responseBody.contains("\"groupId\":\"null\""));	
	}
}
