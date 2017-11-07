package com.example.rad.test.feature.api;

/**
 * Created by Rad on 2017-11-07.
 */

public class ApiException extends Exception {

    private final int statusCode;

    public ApiException(int statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public ApiException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public ApiException(int statusCode, String message, Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public final int getStatusCode() {
        return statusCode;
    }
}