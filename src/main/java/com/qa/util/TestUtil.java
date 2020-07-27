package com.qa.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.data.Users;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;

public class TestUtil {

    public static String getValueByJPath(JSONObject responsejson, String jpath){
        Object obj = responsejson;
        for(String s : jpath.split("/"))
            if(!s.isEmpty())
                if(!(s.contains("[") || s.contains("]")))
                    obj = ((JSONObject) obj).get(s);
                else if(s.contains("[") || s.contains("]"))
                    obj = ((JSONArray) ((JSONObject) obj).get(s.split("\\[")[0])).
                            get(Integer.parseInt(s.split("\\[")[1].replace("]", "")));
        return obj.toString();
    }

    public static String objectToJson(String filename, Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String entryString;
        //Object to json file
        mapper.writeValue(
                new File(System.getProperty("user.dir")+"/src/main/java/com/qa/data/"+filename+".json")
                , object);

        //Object to json string
        entryString = mapper.writeValueAsString(object);
        return entryString;

    }

}
