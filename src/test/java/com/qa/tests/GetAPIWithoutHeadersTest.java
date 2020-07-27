package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;
import com.qa.util.YamlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;

public class GetAPIWithoutHeadersTest extends TestBase {
    TestBase testBase;
    CloseableHttpResponse httpResponse;
    RestClient restClient;
    String serviceUrl;
    String apiUrl;
    String url;

    @BeforeMethod
    public void setUp() throws IOException {
        testBase = new TestBase();
        apiUrl = prop.getProperty("api_url");
        serviceUrl = prop.getProperty("service_url");
        url = apiUrl + serviceUrl;
        restClient = new RestClient();
        httpResponse = restClient.get(url);

    }

    @Test
    public void Test01_verify_response_is_received() throws IOException {
        Assert.assertEquals(restClient.getResponseStatusCode(httpResponse), STATUS_RESPONSE_CODE_200, "[ASSERT FAILED]: Status code is not 200");
    }

    @Test
    public void Test02_verify_response_per_page() throws IOException {
        String per_page = TestUtil.getValueByJPath(restClient.getJsonResponseString(httpResponse), "/per_page");
        Assert.assertEquals(per_page, "6");
    }

    @Test
    public void Test03_verify_response_json_array() throws IOException {
        String last_name = TestUtil.getValueByJPath(restClient.getJsonResponseString(httpResponse), "/data[0]/last_name");
        Assert.assertEquals(last_name, "Bluth");
    }

    @Test
    public void Test04_verify_response_header(){
        HashMap<String, String> headers = restClient.getAllHeadersHashMap(httpResponse);
        String contentType = headers.get("Content-Type");
        Assert.assertTrue(contentType.contains("application/json"), "Content Type header value is incorrect");
    }
}
