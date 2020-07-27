package com.qa.tests;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.UsersPut;
import com.qa.util.YamlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class PutAPITest extends TestBase {
    TestBase testBase;
    RestClient restClient;
    String api_url, service_url, url;
    CloseableHttpResponse closeableHttpResponse;
    YamlReader yamlReader;
    UsersPut users;
    ObjectMapper mapper;

    @BeforeClass
    public void setUp() throws FileNotFoundException {
        testBase = new TestBase();
        restClient = new RestClient();
        yamlReader = new YamlReader("put_request_data.yml");
        api_url = yamlReader.getYamlValue("api_url");
        service_url = yamlReader.getYamlValue("service_url");
        url = api_url + service_url;
        mapper = new ObjectMapper();
    }

    @Test
    public void Test01_verify_response_code_put_request() throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        users = new UsersPut("Josh Cooper", "Software Engineer II");

        String entityString = mapper.writeValueAsString(users);

        closeableHttpResponse = restClient.put(url, entityString, headers);
        Assert.assertEquals(restClient.getResponseStatusCode(closeableHttpResponse), STATUS_RESPONSE_CODE_200);
    }

    @Test
    public void Test02_verify_response_header(){
        HashMap<String, String> header = restClient.getAllHeadersHashMap(closeableHttpResponse);
        String contentType = header.get("Content-Type");
        Assert.assertEquals(contentType, "application/json; charset=utf-8");
    }
    @Test
    public void Test03_verify_json_response() throws IOException {
        String responseString = EntityUtils.toString(closeableHttpResponse.getEntity());
        JSONObject jsonResponse = new JSONObject(responseString);
        UsersPut usersPut = mapper.readValue(responseString, UsersPut.class);
        System.out.println("Updated name is : "+usersPut.getName());
        System.out.println("Updated job is : "+usersPut.getJob());
        Assert.assertEquals(usersPut.getName(), "Josh Cooper");

    }

}
