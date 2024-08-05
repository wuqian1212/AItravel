package com.example.trues.languageModel.entity;

public class Question {
    private String prompt;
    private String content;
    private boolean getTTS;
    private String conversationID;

    public Question(String prompt, String content, boolean getTTS, String conversationID) {
        this.prompt = prompt;
        this.content = content;
        this.getTTS = getTTS;
        this.conversationID = conversationID;
    }

    public Question() {
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String qu) {
        this.prompt = qu;
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
