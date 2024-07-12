package com.example.ai.language;


import com.example.ai.language.impl.ChatAI;

public class Test {
    public static void main(String[] args) {
        ChatConfig config = new ChatConfig();
        config.setModel("ERNIE-Bot-4");
        config.setTemperature(0.7);
        config.setTop_P(0.7);
        config.setMessage("太阳是什么");
        config.setType("two");
        AI ai= new ChatAI();
        ai.chat(config);
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        config.setMessage("你觉得太阳会消失吗");
        ai.chat(config);
    }
}
