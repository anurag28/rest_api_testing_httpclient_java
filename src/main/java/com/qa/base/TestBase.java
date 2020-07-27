package com.qa.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class TestBase{

    public int STATUS_RESPONSE_CODE_200 = 200;
    public int STATUS_RESPONSE_CODE_201 = 201;
    public int STATUS_RESPONSE_CODE_403 = 403;
    public int STATUS_RESPONSE_CODE_500 = 500;
    public int STATUS_RESPONSE_CODE_204 = 204;

    public Properties prop;


    public TestBase(){
        prop = new Properties();
        try{
            FileInputStream fis = new FileInputStream(
              System.getProperty("user.dir") + "/src/main/java/com/qa/config/config.properties"
            );
            prop.load(fis);
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.getMessage();
        }
    }
}
