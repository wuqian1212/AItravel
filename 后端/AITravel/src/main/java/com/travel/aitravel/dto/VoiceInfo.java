package com.travel.aitravel.dto;

public class VoiceInfo {
    private int id;
    private byte[] voice;
    private String afterIp; //后缀域名
    private String username; //用户名
    private String conversationID; // 会话ID

    public VoiceInfo(){}

    public VoiceInfo(int id, byte[] voice, String afterIp, String username, String conversationID) {
        this.id = id;
        this.voice = voice;
        this.afterIp = afterIp;
        this.username = username;
        this.conversationID = conversationID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getVoice() {
        return voice;
    }

    public void setVoice(byte[] voice) {
        this.voice = voice;
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
