package com.example.ai.languageModel.entity;

import com.example.ai.languageModel.AI;
import com.example.ai.languageModel.AIimpl.PauseAI;

public class Observer {//根据observer终止消息
    private String taskID;
    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }


    public void sendToWeb(ApartAnswer apartAnswer){
        System.out.println(taskID);
        //System.out.println("task_id = "+apartAnswer.getTaskID()+"给前端返回："+apartAnswer.getApartAnswer());
    }
    public void pause(){
        AI ai = new PauseAI();
        ai.pause(taskID,"aaa");
    }
}
