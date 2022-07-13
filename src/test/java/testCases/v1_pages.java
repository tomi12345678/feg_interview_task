package testCases;

import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.testBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import utilities.Random_Generator_Utils;

public class v1_pages extends testBase{

	@BeforeClass
	void baseUri()
	{
		httpRequest = RestAssured.given()
		.baseUri("https://api.instatus.com/v1/");
		response = httpRequest.request(Method.GET, "/pages");
	}
	
	@Test
	void checkResponseBody()
	{
		String responseBody = response.getBody().asString();
		assertEquals(responseBody.contains(id), true);
		System.out.println(responseBody);
	}
	
	@Test
	void checkStatusCode()
	{
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
		System.out.println("Status Code is: " + statusCode);
	}
	
	@Test
	void checkResponseTime()
	{
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime < 2000);
		System.out.println("Response time is: " + responseTime);
	}
	
	@Test
	void checkStatusLine()
	{
		String statusLine = response.getStatusLine();
		assertEquals(statusLine, "HTTP/1.1 200 OK");
		System.out.println("Status line is: " + statusLine);
	}
	
	@Test
	void checkContentType()
	{
		String contentType = response.getContentType();
		assertEquals(contentType, "application/json; charset=utf-8");
		System.out.println("Status line is: " + contentType);
	}
	
	@Test
	void checkContentLength()
	{
		String contentLength = response.header("Content-Length");
		Assert.assertTrue(Integer.parseInt(contentLength)>100);;		
	}
	
	@Test
	void verifyQueryParameters()
	{
		httpRequest = RestAssured.given()
		.queryParam("name", "12345");
		String responseBody = response.getBody().asString();
		assertEquals(responseBody, "[]");
		System.out.println(responseBody);	
		// all filter & select query parameters do not actually filter the responses, so this test fails
	}
	
	@Test
	void verifyPostStatusCode()
	{
		String subdomain = Random_Generator_Utils.subdomain();
		JSONObject requestParams = new JSONObject();
		requestParams.put("subdomain", subdomain);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "pages");
		String repsonseBody = response.getBody().asString();
		System.out.println(repsonseBody);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 429);
		System.out.println("Status Code is: " + statusCode);
	}
	
	@Test
	void verifyPutStatusCode()
	{
		String subdomain = Random_Generator_Utils.subdomain();
		JSONObject requestParams = new JSONObject();
		requestParams.put("subdomain", subdomain);
		
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.PUT, "pages");
		String repsonseBody = response.getBody().asString();
		System.out.println(repsonseBody);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 401);
		System.out.println("Status Code is: " + statusCode);
	}
	
	@Test
	void verifyDeleteStatusCode()
	{		
		Response response = httpRequest.request(Method.DELETE, "pages");
		String repsonseBody = response.getBody().asString();
		System.out.println(repsonseBody);
		
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 401);
		System.out.println("Status Code is: " + statusCode);
	}
	
}
