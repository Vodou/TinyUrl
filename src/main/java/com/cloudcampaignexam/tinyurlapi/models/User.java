package com.cloudcampaignexam.tinyurlapi.models;

/**
 * User
 */
public class User {
    private String id;
    private String username;
    private String email;
    private String accountInfo;

    public User() {
        super();
    }

    public User(String id, String username, String email, String accountInfo) {
        super();
        this.id = id;
        this.username = username;
        this.email = email;
        this.accountInfo = accountInfo;
    }

    /**
     * @return the accountInfo
     */
    public String getAccountInfo() {
        return accountInfo;
    }
    /**
     * @param accountInfo the accountInfo to set
     */
    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }
    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }
    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

}