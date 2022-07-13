package testCases;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

import org.json.simple.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import base.testBase;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;

public class v1_page_id_components_component_id extends testBase {

	@BeforeClass
	void setParameters()
	{
		httpRequest = RestAssured.given()
		.basePath("{page_id}/{component_id}")
		.pathParams("page_id", pageId, "component_id", component_id);
		response = httpRequest.request(Method.PUT, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
	}

	@Test
	void verifyGetSingleRecord()
	{
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		String id_count[] = responseBody.split(":");
		int count = 0;
		int i;
		String j;
		for (i = 0; i < id_count.length; i++)
		{
			j = id_count[i];
			if (j.contains("\"id\""))
			{
				count ++;	
			}
		}
		assertTrue(count == 1);
	}
		
	@Test
	void verifyPutRequest()
	{
		httpRequest = RestAssured.given()
		.basePath("{page_id}/{component_id}")
		.pathParams("page_id", pageId, "component_id", component_id);
		response = httpRequest.request(Method.PUT, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", "PUT CHANGE TESTED");
		requestParams.put("status", "UNDERMAINTENANCE");
		requestParams.put("description", "Put description");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.PUT, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		assertEquals(responseBody.contains("PUT CHANGE TESTED"), true);
		assertEquals(responseBody.contains("UNDERMAINTENANCE"), true);
		assertEquals(responseBody.contains("Put description"), true);
		
		// PUT values back to the initial values 
		JSONObject putOldParamValues = new JSONObject();
		putOldParamValues.put("name", name);
		putOldParamValues.put("status", status);
		putOldParamValues.put("description", description);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(putOldParamValues.toJSONString());
		response = httpRequest.request(Method.PUT, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
	}

	@Test
	void deleteRequest()
	{
		httpRequest = RestAssured.given()
		.baseUri("https://api.instatus.com/v1/")
		.basePath("{page_id}")
		.pathParam("page_id", pageId);
		JSONObject requestParams = new JSONObject();
		requestParams.put("name", name);
		requestParams.put("description", description);
		requestParams.put("status", status);
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.request(Method.POST, "/components");
		String responseBody = response.getBody().asString();
		System.out.println(responseBody);
		String created_id = responseBody.substring(7, 35).replaceAll("\"", "").replaceAll(",", "");
		System.out.println(created_id);
		
		httpRequest = RestAssured.given()
		.basePath("{page_id}/{component_id}")
		.pathParams("page_id", pageId, "component_id", created_id);
		response = httpRequest.request(Method.DELETE, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
		
		httpRequest = RestAssured.given()
		.basePath("{page_id}/{component_id}")
		.pathParams("page_id", pageId, "component_id", created_id);
		response = httpRequest.request(Method.GET, "https://api.instatus.com/v1/{page_id}/components/{component_id}");
		assertEquals(response.statusCode(), 500);
	}
	
	
		// created at, updated at, siteID is consistent, nameHTML matches name, same for descriptionHtml, order number after DELETE request
	
}
