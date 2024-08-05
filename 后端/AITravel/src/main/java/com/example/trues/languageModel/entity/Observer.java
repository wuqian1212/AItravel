package com.example.trues.languageModel.entity;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Observer {
    private String taskID;
    private SseEmitter emitter;

    public Observer(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public void outVoiceToWeb(String result) {
        // Handle voice return logic here, this is just an example
        System.out.println(result);
    }

    public void outTextToWeb(ApartAnswer apartAnswer) {
        if (apartAnswer == null) {
            System.err.println("Error: Attempted to send null data.");
            return;
        }
        Object data = apartAnswer.getApartAnswer();
        if (data == null) {
            System.err.println("Error: Attempted to send null data.");
            return;
        }
        try {
            System.out.print(data); // Add logging
            emitter.send(data.toString());
        } catch (Exception e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }

    public void pause() {
        // Pause logic
        // System.out.println(taskID);
        // AI ai = new DifyAI("app-JTraL0zj8wbWFaoONP6NzpGi");
        // ((DifyAI)ai).pause(taskID, "aaa");
    }
}
