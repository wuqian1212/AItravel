package com.example.ai.languageModel.entity;

public class Question {
    private String qu;
    private String content;
    private boolean getTTS;
    private String conversationID;

    public Question(String qu, String content, boolean getTTS, String conversationID) {
        this.qu = qu;
        this.content = content;
        this.getTTS = getTTS;
        this.conversationID = conversationID;
    }

    public String getQu() {
        return qu;
    }

    public void setQu(String qu) {
        this.qu = qu;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isGetTTS() {
        return getTTS;
    }

    public void setGetTTS(boolean getTTS) {
        this.getTTS = getTTS;
    }

    public String getConversationID() {
        return conversationID;
    }

    public void setConversationID(String conversationID) {
        this.conversationID = conversationID;
    }
}
