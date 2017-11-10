package com.example.rad.test.feature.data;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Category {


    public final String key;
    public final String name;


    public Category() {
        this.key = null;
        this.name = null;
    }
    public static Category fromJsonObject(JSONObject jsonObject) {
        try {
            return new Category(jsonObject.getString("key"), jsonObject.getString("name"));
        } catch (JSONException exp) {
            Log.e("category object", exp.getMessage());
        }
        return null;
    }


    private Category(String key, String name) {
        this.key = key;
        this.name = name ;
    }

    @Override
    public String toString() {
        return "key=" + key + " name= " + name;
    }

}
