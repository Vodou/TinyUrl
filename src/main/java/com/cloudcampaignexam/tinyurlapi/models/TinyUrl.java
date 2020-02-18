package com.cloudcampaignexam.tinyurlapi.models;

import java.util.Date;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.URL;



/**
 * TinyUrl model object to hold Tiny url data Should do more reseach to see if
 * there's a base class I can inherit from with more appropriate functionality
 */
public class TinyUrl {
    //For security reasons id should not be the same as tinyURL suffix. Users shouldn't be able to guess an ID
    private String id;
    //will denormalize relationship for now for ease of implementation user should probably be handled in a
    //join if not expensive
    private String userId;
    private String baseUrl;
    private String tinyUrlSuffix;
    @NotBlank(message = "realUrl must not be null or empty")
    @URL(message = "realUrl must be formatted as URL. Protocol is required.")
    private String realUrl;
    private Date createdDate;

    public TinyUrl() {

    }

    public TinyUrl(String id, String userId, String baseUrl, String tinyUrlSuffix, String realUrl) {
        super();
        this.id = id;
        this.userId = userId;
        this.baseUrl = baseUrl;
        this.tinyUrlSuffix = tinyUrlSuffix;
        this.realUrl = realUrl;
        this.createdDate = new Date(System.currentTimeMillis());
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * @return the realUrl
     */
    public String getRealUrl() {
        return realUrl;
    }
    /**
     * @param realUrl the realUrl to set
     */
    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }
    /**
     * @return the tinyUrl
     */
    public String getTinyUrl() {
        return baseUrl + tinyUrlSuffix;
    }
    /**
     * @return the createdDate
     */
    public Date getCreatedDate() {
        return createdDate;
    }
    /**
     * @param createdDate the createdDate to set
     */
    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
    /**
     * @return the baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }
    /**
     * @return the tinyUrlSuffix
     */
    public String getTinyUrlSuffix() {
        return tinyUrlSuffix;
    }
    /**
     * @param tinyUrlSuffix the tinyUrlSuffix to set
     */
    public void setTinyUrlSuffix(String tinyUrlSuffix) {
        this.tinyUrlSuffix = tinyUrlSuffix;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }




    
}