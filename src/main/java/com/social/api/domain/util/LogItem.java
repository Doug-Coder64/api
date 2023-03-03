package com.social.api.domain.util;

import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.*;

@Document
public class LogItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    private String logLevel;
    private String message;
    private String date;

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public LogItem(String logLevel, String message, String date) {
        super();
        this.logLevel = logLevel;
        this.message = message;
        this.date = date;

    }

}
