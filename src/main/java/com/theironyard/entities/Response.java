package com.theironyard.entities;

import org.springframework.http.HttpStatus;

public class Response {
    public Object body;
    public String errorMessage;
    public Boolean wasError;
    public HttpStatus status;

    public Response(Object body) {
        this.body = body;
        wasError = false;
        this.status = HttpStatus.OK;
    }

    public Response(String errorMessage, HttpStatus status) {
        this.errorMessage = errorMessage;
        wasError = true;
        this.status = status;
    }
}
