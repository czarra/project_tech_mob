package com.example.rad.test.feature.data;

import android.util.Log;

import com.example.rad.test.feature.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Articles {

    public static Articles fromJsonObject(JSONObject jsonObject) {
        try {
            String logoLargeUrl;
            try{
                logoLargeUrl = jsonObject.getJSONObject( "brand").getString("logoLargeUrl");
            }  catch (JSONException exp) {
                logoLargeUrl ="";
            }
            return new Articles(jsonObject.getString("id"), jsonObject.getString("name"),
                    jsonObject.getString("shopUrl"), jsonObject.getJSONObject("media").getJSONArray("images"),
                    jsonObject.getString("color"), jsonObject.getString("season"),
                    jsonObject.getString("seasonYear"), logoLargeUrl ,
                    jsonObject.getJSONArray("units"), jsonObject.getString("available"));
        } catch (JSONException exp) {
            Log.e("article class", exp.getMessage());
        }
        return null;
    }

    public final String id;
    public final String name;
    public final String shopUrl;
    public final JSONArray  url;
    public final String color;
    public final String season;
    public final String seasonYear;
    public final String logo;
    public final JSONArray  units;
    public final String available;

    public Articles() {
        this(null,null,null,null,null,null,null,null,null, null);
    }


    private Articles(String id, String name, String shopUrl, JSONArray url, String color, String season, String seasonYear, String logo,  JSONArray units, String available ) {
        this.id = id;
        this.name = name;
        this.shopUrl = shopUrl;
        this.url = url;
        this.color = color;
        this.season = season;
        this.seasonYear = seasonYear;
        if(logo.isEmpty()){
            logo = "https://www.smartdroid.de/wp-content/uploads/2017/07/zalando-logo.jpg";
        }
        this.logo = logo;
        this.units = units;
        this.available = available;
    }

    @Override
    public String toString() {
        return "id=" + id + " name= " + name + ", season=" + season;
    }

}
