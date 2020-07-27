package com.qa.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;
import com.qa.util.TestUtil;
import com.qa.util.YamlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class PostAPITest extends TestBase {
    TestBase testBase;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    String apiUrl, serviceUrl, url;
    YamlReader yamlReader;
    ObjectMapper mapper;
    Users user;

    @BeforeClass
    public void setUpBeforeClass() throws FileNotFoundException {
        testBase = new TestBase();
        yamlReader = new YamlReader("post_request_data.yml");
        apiUrl = yamlReader.getYamlValue("api_url");
        serviceUrl = yamlReader.getYamlValue("service_url");
        url = apiUrl + serviceUrl;
        restClient = new RestClient();
        mapper = new ObjectMapper();
    }

    @Test
    public void Test01_verify_response_code() throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        user = new Users("test name", "test job");

        String entityString;
        //Object to json file
        mapper.writeValue(
                new File(System.getProperty("user.dir")+"/src/main/java/com/qa/data/users.json")
                , user);

        //Object to json string
        entityString = mapper.writeValueAsString(user);
        closeableHttpResponse = restClient.post(url, entityString, headers);
        Assert.assertEquals(restClient.getResponseStatusCode(closeableHttpResponse), STATUS_RESPONSE_CODE_201);
    }

    @Test
    public void Test02_verify_response_header(){
        HashMap<String, String> headers = restClient.getAllHeadersHashMap(closeableHttpResponse);
        String contentType = headers.get("Content-Type");
        Assert.assertTrue(contentType.contains("application/json"), "Content Type header value is incorrect");
    }

    @Test
    public void Test03_verify_json_response() throws IOException {
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
        JSONObject responseJsonObject = new JSONObject(responseString);

        //JSON to java object
        Users postedUser = mapper.readValue(responseString, Users.class);
        Assert.assertEquals(user.getName(), postedUser.getName(), "User was not created");
    }
}
