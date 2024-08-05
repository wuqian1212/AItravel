package com.example.trues.entity;

public class SenseVoiceResult {
    private String text;
    private String emotion;
    private String type;

    public SenseVoiceResult(String text, String emotion, String type) {
        this.text = text;
        this.emotion = emotion;
        this.type = type;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {

        this.text = text;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
