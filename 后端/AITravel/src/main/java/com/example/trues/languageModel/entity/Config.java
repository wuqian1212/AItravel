package com.example.trues.languageModel.entity;

public class Config {
    private String conversationID;
    private String username;
    private String afterIp;

    public Config(String conversationID, String username, String afterIp) {
        this.conversationID = conversationID;
        this.username = username;
        this.afterIp = afterIp;
    }

    public Config() {
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAfterIp() {
        return afterIp;
    }

    public void setAfterIp(String afterIp) {
        this.afterIp = afterIp;
    }
}
