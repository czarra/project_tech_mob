package com.example.rad.test.feature.data;

import android.widget.ImageView;

import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Articles_picture_single {

    public static Articles_picture_single fromJsonObject(JSONObject jsonObject, ImageView img) {
        try {
            return new Articles_picture_single(jsonObject.getString("orderNumber"), jsonObject.getString("mediumHdUrl"),img);
        } catch (JSONException exp) {

        }
        return null;
    }

    public final String id;
    public final String name;
    public final ImageView img;

    public Articles_picture_single() {
        this(null,null,null);
    }


    private Articles_picture_single(String id, String name,ImageView img ) {
        this.id = id;
        this.name = name;
        this.img = img;

    }

    @Override
    public String toString() {
        return "id=" + id + " name= " + name ;
    }

}
