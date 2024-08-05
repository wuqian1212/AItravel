package com.example.trues.languageModel.entity;

public class ApartAnswer {
    private String taskID;;
    private String apartAnswer;

    public ApartAnswer(String taskID, String apartAnswer) {
        this.taskID = taskID;
        this.apartAnswer = apartAnswer;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public String getApartAnswer() {
        return apartAnswer;
    }

    public void setApartAnswer(String apartAnswer) {
        this.apartAnswer = apartAnswer;
    }
}
