package com.example.rad.test.feature.data;

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


    public Category(String key) {
        this.key = key;
        this.name = this.changeOnName(key) ;
    }

    @Override
    public String toString() {
        return "key=" + key + " name= " + name;
    }

    private String changeOnName(String key){
        String name = key.replace("-"," ");

        return name.toUpperCase();
    }

}
