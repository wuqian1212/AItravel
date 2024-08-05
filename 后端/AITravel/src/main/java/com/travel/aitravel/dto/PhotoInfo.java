package com.travel.aitravel.dto;

public class PhotoInfo {
    private int id;
    private byte[] photo;
    private String afterIp; //后缀域名
    private String username; //用户名
    private String conversationID; // 会话ID

    public PhotoInfo(int id, byte[] photo, String afterIp, String username, String conversationID) {
        this.id = id;
        this.photo = photo;
        this.afterIp = afterIp;
        this.username = username;
        this.conversationID = conversationID;

    }

    public PhotoInfo() {
    }

    //getter and setter

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

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
}
