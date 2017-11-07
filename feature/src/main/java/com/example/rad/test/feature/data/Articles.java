package com.example.rad.test.feature.data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Articles {

    public static Articles fromJsonObject(JSONObject jsonObject) {
        try {
            return new Articles(jsonObject.getString("id"), jsonObject.getString("name"),
                    jsonObject.getString("shopUrl"), jsonObject.getJSONObject("media").getJSONArray("images"),
                    jsonObject.getString("color"), jsonObject.getString("season"),
                    jsonObject.getString("seasonYear"),  jsonObject.getJSONObject( "brand").getString("logoLargeUrl"));
        } catch (JSONException exp) {

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

    public Articles() {
        this(null,null,null,null,null,null,null,null);
    }


    private Articles(String id, String name, String shopUrl, JSONArray url, String color, String season, String seasonYear, String logo ) {
        this.id = id;
        this.name = name;
        this.shopUrl = shopUrl;
        this.url = url;
        this.color = color;
        this.season = season;
        this.seasonYear = seasonYear;
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "id=" + id + " name= " + name + ", season=" + season;
    }

}
