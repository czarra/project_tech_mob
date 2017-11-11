package com.example.rad.test.feature.data;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Rad on 2017-11-06.
 */

public class Articles_size_single implements Comparable<Articles_size_single>{

    public static Articles_size_single fromJsonObject(JSONObject jsonObject, TextView text) {
        try {

            return new Articles_size_single(jsonObject.getString("id"), jsonObject.getString("size"),jsonObject.getJSONObject("price").getString("formatted"), text,
                    jsonObject.getJSONObject("price").getString("currency"),jsonObject.getJSONObject("price").getString("value"));
        } catch (JSONException exp) {
            Log.e("error Article Size",exp.getMessage());
        }
        return null;
    }

    public final String id;
    public final String size;
    public final String price;
    public final TextView text;
    public final String currency;
    public final String value;


    public Articles_size_single() {
        this(null,null,null,null, null, null);
    }


    private Articles_size_single(String id, String size, String price, TextView text ,String currency,String value) {
        Log.d("id", id);
        Log.d("size", size);
        Log.d("price", price);
        this.id = id;
        this.size = size;
        this.currency = currency;
        this.value = value;
        if(currency.equalsIgnoreCase("PLN")){
            price =  String.format("%.2f",  Double.parseDouble(value));
            price += " zł";
        }
        this.price = price;
        this.text = text;

    }

    @Override
    public String toString() {
        return "id=" + id + " size= " + size ;
    }

    @Override
    public int compareTo(@NonNull Articles_size_single articles_size_single) {
        if (stringToInt(this.size) > stringToInt(articles_size_single.size))
            return 1;
        else if (stringToInt(this.size) == stringToInt(articles_size_single.size))
            return 0;
        return -1;

    }

    private int stringToInt(String text){
        try {
            return Integer.parseInt(text);
        }
        catch (NumberFormatException  e){
            return 0;
        }
    }
}
