package com.travel.aitravel.dto;

public class ChatRequest {
    private String afterIp;
    private String username;
    private String conversationID;
    private String content;
    private int id;


    public String getAfterIp() {
        return afterIp;
    }

    public void setAfterIp(String afterIp) {
        this.afterIp = afterIp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
