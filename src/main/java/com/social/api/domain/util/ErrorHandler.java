package com.social.api.domain.util;

public class ErrorHandler {

    private Exception error;
    private Object errorObject;

    public void setError(Exception error) {
        this.error = error;
    }

    public Object getError(){
        this.errorObject = error.getMessage();
        return errorObject;
    }
    public ErrorHandler(Exception error) {
        this.error = error;
    }
}
