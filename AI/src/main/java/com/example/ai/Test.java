package com.example.ai;

import com.example.ai.languageModel.AIimpl.ChatAI;
import com.example.ai.languageModel.entity.Config;
import com.example.ai.languageModel.entity.Message;
import com.example.ai.languageModel.entity.Observer;
import com.example.ai.languageModel.entity.Question;

public class Test {
    public static void main(String[] args) {
        ChatAI chatAI = new ChatAI();
        Message me = chatAI.chat(new Config("","aaa","chat-messages"),new Question("{}","你可以给自己起个名字吗",false,""),new Observer());
        System.out.println(me.getConversation()+"\t"+me.getMessageID()+"\t"+me.getTaskID()+"\t"+me.getTime());
    }

}
