package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPIWithHeadersTest extends TestBase {

    TestBase testBase;
    RestClient restClient;
    CloseableHttpResponse closeableHttpResponse;
    String apiUrl, serviceUrl, url;

    @BeforeClass
    public void setUpBeforeClass(){
        testBase = new TestBase();
        apiUrl = prop.getProperty("api_url");
        serviceUrl = prop.getProperty("service_url");
        url = apiUrl + serviceUrl;
        restClient = new RestClient();
    }


    @BeforeMethod
    public void setUp() throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("Content-Type","application/json");
        closeableHttpResponse = restClient.get(url,headers);
    }

    @Test
    public void Test01_verify_response_for_request_with_header() throws IOException {

        Assert.assertEquals(restClient.getResponseStatusCode(closeableHttpResponse), STATUS_RESPONSE_CODE_200);
    }

    @Test
    public void Test02_verify_response_per_page() throws IOException {
        String per_page = TestUtil.getValueByJPath(restClient.getJsonResponseString(closeableHttpResponse), "/per_page");
        Assert.assertEquals(per_page, "6");
    }

    @Test
    public void Test03_verify_response_json_array() throws IOException {
        String last_name = TestUtil.getValueByJPath(restClient.getJsonResponseString(closeableHttpResponse), "/data[0]/last_name");
        Assert.assertEquals(last_name, "Bluth");
    }

    @Test
    public void Test04_verify_response_header(){
        HashMap<String, String> headers = restClient.getAllHeadersHashMap(closeableHttpResponse);
        String contentType = headers.get("Content-Type");
        Assert.assertTrue(contentType.contains("application/json"), "Content Type header value is incorrect");
    }
}
