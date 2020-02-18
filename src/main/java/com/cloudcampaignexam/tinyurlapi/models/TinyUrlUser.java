package com.cloudcampaignexam.tinyurlapi.models;

/**
 * TinyUrlUser
 * 
 */
public class TinyUrlUser {
    private String userId;
    private String tinyUrlId;

    public TinyUrlUser() {
        super();
    }

    public TinyUrlUser(String userId, String tinyUrlId) {
        super();
        this.tinyUrlId = tinyUrlId;
        this.userId = userId;
    }

    /**
     * @return the tinyUrlId
     */
    public String getTinyUrlId() {
        return tinyUrlId;
    }
    /**
     * @param tinyUrlId the tinyUrlId to set
     */
    public void setTinyUrlId(String tinyUrlId) {
        this.tinyUrlId = tinyUrlId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUser(String userId) {
        this.userId = userId;
    }
    /**
     * @return the user
     */
    public String getUserId() {
        return userId;
    }
    

    
}