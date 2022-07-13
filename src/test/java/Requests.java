import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.authentication.PreemptiveBasicAuthScheme;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class Requests {

	@Test
	public void GET_validate_status_code_and_status_line()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "path_parameter");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// status code validation
		int status_code = response.getStatusCode();
		Assert.assertEquals(status_code, 200 );
		
		// status line validation
		String status_line = response.getStatusLine();
		Assert.assertEquals(status_line, "HTTP/1.1 200 OK");
		
	}
	
	
	@Test
	public void POST_request_one()
	{
		// post requires URI, request, response ( like get)
		// 
		
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// request payload sending along with post request
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "JohnXYZ");
		requestParams.put("LastName", "CenaXYZ");
		requestParams.put("UserName", "JohnUserName");
		requestParams.put("Password", "pass");
		requestParams.put("Email", "johncena@gmail.com");
		
		httpRequest.header("Content-Type","application/json");
		httpRequest.body(requestParams.toJSONString());
		
		//response object
		Response response = httpRequest.request(Method.POST, "/register");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// status code validation
		int status_code = response.getStatusCode();
		System.out.println("status code is:" + status_code);
		Assert.assertEquals(status_code, 201);
		
		// success code validation
		String success_code = response.jsonPath().get("successCode");
		Assert.assertEquals(success_code, "OPERATION_SUCCESS");
		
		// status line validation
		String status_line = response.getStatusLine();
		System.out.println("status code is:" + status_line);
		Assert.assertEquals(status_code, 201);
		
		
	}
	
	@Test
	public void GET_validate_headers()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "path_parameter");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// BEFORE validating headers, they should be validated in postman!!!
		// copy request URL (along with key parameter that is in the URL) to postman, send request, check headers tab
		
		// validating headers
		String content_type = response.header("Content-Type"); // capture details of Content-Type header
		System.out.println("content type is:" + content_type);
		Assert.assertEquals(content_type, "application/xmp; charset=UTF-8");
		
		String content_encoding = response.header("Content-Encoding"); // capture details of Content-Encoding header
		System.out.println("content type is:" + content_encoding);
		Assert.assertEquals(content_encoding, "gzip");
		
	}
	
	@Test
	public void GET_print_all_headers()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "path_parameter");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// BEFORE validating headers, they should be validated in postman!!!
		// copy request URL (along with key parameter that is in the URL) to postman, send request, check headers tab
		
		// validating all headers
		Headers all_headers = response.headers();	
		for(Header header:all_headers)
		{
			System.out.println(header.getName() + "       " + header.getValue());
		}
	}
	
	@Test
	public void GET_validate_JSON_response()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "/Delhi");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// verify response body is correct
		Assert.assertEquals(responseBody.contains("Delhi"), true);	
	}
	
	@Test
	public void GET_validate_all_response_values()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "/Delhi");
		
		// capture values into JsonPath
		JsonPath jsonpath = response.jsonPath();
		jsonpath.get("City");
		jsonpath.get("Temperature");
		jsonpath.get("Humdity");
		
		Assert.assertEquals(jsonpath.getBoolean("Temperature"), "39 Degreee celsius");
	}
	
	@Test
	public void Autherization()
	{
		// base URI
		RestAssured.baseURI = "link";
		
		
		//BASIC AUTHENTICATION
		PreemptiveBasicAuthScheme auth = new PreemptiveBasicAuthScheme();
		auth.setUserName("USERNAME");
		auth.setPassword("PASSWORD");
		RestAssured.authentication=auth;
		
		
		// request object
		RequestSpecification httpRequest = RestAssured.given();
		
		// response object
		Response response = httpRequest.request(Method.GET, "/Delhi");
		
		// print response in console
		String responseBody = response.getBody().asString();
		System.out.println("response body is:" + responseBody);
		
		// status code validation
		int status_code = response.getStatusCode();
		Assert.assertEquals(status_code, 200 );
		
		// status line validation
		String status_line = response.getStatusLine();
		Assert.assertEquals(status_line, "HTTP/1.1 200 OK");
		
		
	}
	
}
