package com.example.trues;

import com.example.trues.languageModel.AIimpl.DifyAI;
import com.example.trues.languageModel.entity.Config;
import com.example.trues.languageModel.entity.Observer;
import com.example.trues.languageModel.entity.Question;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Test {
    public static void main(String[] args) {
    AI ai = new DifyAI("app-JTraL0zj8wbWFaoONP6NzpGi");
        SseEmitter n = new SseEmitter();
        Observer observer = new Observer(n);
        //ai.callVoice(new Config("","aaa","chat-messages"),new Question("{\"age\":\"20\",\"career\":\"学生\",\"sex\":\"男\",\"like\":\"攀岩\"}","hello",false,""),"F:\\AITraining\\Project\\AITravel\\AITravel\\AITravel\\received_voice.wav",observer);
        ai.callText(new Config("","aaa","chat-messages"),new Question("{\"age\":\"20\",\"career\":\"学生\",\"sex\":\"男\",\"like\":\"攀岩\"}","hello",false,""),observer);
    }
}
