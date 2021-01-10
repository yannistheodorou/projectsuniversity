package com.katanemimena.util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * It contains convenient methods
 */
public class Util {
    public static List<JSONObject> convertJSONArrayIntoListOfJSONObjects(JSONArray jsonArray){
        try{
            ArrayList<JSONObject> result = new ArrayList<>();
            for (int i = 0; i < jsonArray.length() ; i++) {
                result.add(jsonArray.getJSONObject(i));
            }
            return result;
        }catch (Exception e){
            return null;
        }
    }


    public static String getString(JSONObject json, String key){
        try{
            return json.getString(key);
        }catch (Exception e){
            return "";
        }
    }
}
