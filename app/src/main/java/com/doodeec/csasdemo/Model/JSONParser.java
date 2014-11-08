package com.doodeec.csasdemo.Model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Dusan Doodeec Bartos on 20.10.2014.
 *
 * Simple JSON parser - wraps methods around try-catch blocks
 * Handles JSON exceptions
 */
public class JSONParser {

    protected static Integer getIntForKey(JSONObject jsonObject, String key) {
        Integer value = null;
        try {
            value = jsonObject.getInt(key);
        } catch (JSONException e) {
            Log.e("Error reading Integer", e.getMessage() + " in object: " + jsonObject.toString());
        }
        return value;
    }

    protected static String getStringForKey(JSONObject jsonObject, String key) {
        String value = null;
        try {
            value = jsonObject.getString(key);
        } catch (JSONException e) {
            Log.e("Error reading string", e.getMessage() + " in object: " + jsonObject.toString());
        }
        return value;
    }

    protected static Double getDoubleForKey(JSONObject jsonObject, String key) {
        Double value = null;
        try {
            value = jsonObject.getDouble(key);
        } catch (JSONException e) {
            Log.e("Error reading double", e.getMessage() + " in object: " + jsonObject.toString());
        }
        return value;
    }

    protected static JSONObject getObjectForKey(JSONObject jsonObject, String key) {
        JSONObject value = null;
        try {
            value = jsonObject.getJSONObject(key);
        } catch (JSONException e) {
            Log.e("Error reading JSON object", e.getMessage() + " in object: " + jsonObject.toString());
        }
        return value;
    }

    protected static JSONArray getArrayForKey(JSONObject jsonObject, String key) {
        JSONArray value = null;
        try {
            value = jsonObject.getJSONArray(key);
        } catch (JSONException e) {
            Log.e("Error reading JSON array", e.getMessage() + " in object: " + jsonObject.toString());
        }
        return value;
    }
}
