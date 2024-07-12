package com.example.ai.language;

import com.baidubce.qianfan.model.chat.Message;

import java.util.ArrayList;
import java.util.List;

public class ChatConfig extends Config {
    //通过筛选到底进行多轮对话还是单论对话
    private String type;
    private ArrayList<Message> messageArrayList;


    public ArrayList<Message> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<Message> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
