package com.qa.util;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

public class YamlReader {

    private String fileDir = System.getProperty("user.dir")+"/src/main/resources/TestData/";
    private String filePath;

    public YamlReader(String fileName){
         filePath = fileDir + fileName;
    }

    public String getYamlValue(String key) throws FileNotFoundException {
        Yaml yaml = new Yaml();
        InputStream inputStream = new FileInputStream(filePath);
        Map<String, Object> keyValueMap = yaml.load(inputStream);
        return keyValueMap.get(key).toString();
    }

}
