package com.example.rad.test.feature.api;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rad on 2017-11-07.
 */


class ConnectionBuilder {



    static final int BUFFER_SIZE = 4096;

    private String method;
    private String url;
    private String contentType = "application/json";
    private String data;
    private String token;
    private final String boundary;

    ConnectionBuilder() {
        this.boundary = "IT" + System.currentTimeMillis();
    }

    ConnectionBuilder withUrl(String url) {
        this.url = url;
        return this;
    }


    HttpURLConnection get() throws ApiException {
        this.method = "GET";
        return build();
    }

    HttpURLConnection put(String data) throws ApiException {
        this.method = "PUT";
        this.data = data;
        return build();
    }

    HttpURLConnection post(String data) throws ApiException {
        this.method = "POST";
        this.data = data;
        return build();
    }

    HttpURLConnection post() throws ApiException {
        return post(null);
    }

    private HttpURLConnection build() throws ApiException {

        try {
            final HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod(method);

            connection.connect();
            return connection;
        } catch (IOException e) {
            throw new ApiException(500, "IOException", e);
        }
    }

}