package pers.hyu.jwtdemo.exception;

import java.util.Date;

public class ErrorMap {
    private Date timestamp;
    private String message;

    public ErrorMap() {
    }

    public ErrorMap(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
