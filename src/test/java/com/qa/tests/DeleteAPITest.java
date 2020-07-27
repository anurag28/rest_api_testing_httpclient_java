package com.qa.tests;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.YamlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class DeleteAPITest extends TestBase {
    TestBase testBase;
    RestClient restClient;
    String api_url, service_url, url;
    CloseableHttpResponse closeableHttpResponse;
    YamlReader yamlReader;

    @BeforeClass
    public void setUp() throws FileNotFoundException {
        testBase = new TestBase();
        yamlReader = new YamlReader("delete_request_data.yml");
        api_url = yamlReader.getYamlValue("api_url");
        service_url = yamlReader.getYamlValue("service_url");
        url = api_url + service_url;
        restClient = new RestClient();
    }

    @Test
    public void Test01_verify_delete_response_code() throws IOException {
        closeableHttpResponse = restClient.delete(url);
        System.out.println("Actual response Code: "+restClient.getResponseStatusCode(closeableHttpResponse));
        Assert.assertEquals(restClient.getResponseStatusCode(closeableHttpResponse), STATUS_RESPONSE_CODE_204);
    }


}
