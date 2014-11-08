package com.doodeec.csasdemo.ServerRequest;

/**
 * Created by Dusan Doodeec Bartos on 26.10.2014.
 *
 * Server/Client error response
 * Used for both server error responses and successful server responses which lead to incorrect data -> client error responses
 */
public class ErrorResponse {
    private String message;

    public ErrorResponse(String errorMessage) {
        this.message = errorMessage;
    }

    public String getMessage() {
        return message;
    }
}

