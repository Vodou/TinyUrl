package com.cloudcampaignexam.tinyurlapi.exceptions;

import java.sql.Date;
import java.util.Collection;

public class ErrorResponse {
    private Date timeStamp;
    private String message;
    private Collection<String> details;

    public ErrorResponse( String message, Collection<String> details) {
        super();
        this.message = message;
        this.details = details;
        this.timeStamp = new Date(System.currentTimeMillis());
    }
    /**
     * @return the details
     */
    public Collection<String> getDetails() {
        return details;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }/**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }
}
