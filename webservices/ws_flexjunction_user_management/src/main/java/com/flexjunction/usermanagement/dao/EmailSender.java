package com.flexjunction.usermanagement.dao;

public class EmailSender {
    private String fromEmail;
    private String apiKey;

    public EmailSender(String fromEmail, String apiKey) {
        this.fromEmail = fromEmail;
        this.apiKey = apiKey;
    }

    public String getFromEmail() {
        return fromEmail;
    }

    public String getApiKey() {
        return apiKey;
    }
}
