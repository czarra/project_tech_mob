package com.example.rad.test.feature.data;

import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Articles_size_single {

    public static Articles_size_single fromJsonObject(JSONObject jsonObject, TextView text) {
        try {

            return new Articles_size_single(jsonObject.getString("id"), jsonObject.getString("size"),jsonObject.getJSONObject("price").getString("formatted"), text);
        } catch (JSONException exp) {
            Log.e("error Article Size",exp.getMessage());
        }
        return null;
    }

    public final String id;
    public final String size;
    public final String price;
    public final TextView text;

    public Articles_size_single() {
        this(null,null,null,null);
    }


    private Articles_size_single(String id, String size, String price, TextView text ) {
        Log.d("id", id);
        Log.d("size", size);
        Log.d("price", price);
        this.id = id;
        this.size = size;
        this.price = price;
        this.text = text;

    }

    @Override
    public String toString() {
        return "id=" + id + " size= " + size ;
    }

}
