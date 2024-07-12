package com.example.ai.language.impl;

import com.baidubce.qianfan.Qianfan;
import com.baidubce.qianfan.core.builder.ChatBuilder;
import com.baidubce.qianfan.core.builder.MessageBuilder;
import com.baidubce.qianfan.model.chat.ChatResponse;
import com.baidubce.qianfan.model.chat.Message;
import com.example.ai.language.*;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;

public class ChatAI implements AI {//可以理解成一次对话
    private Qianfan base = new Qianfan(FinalTools.AK,FinalTools.SK);
    private ArrayList<Message> arrayList = new ArrayList<>();
    private Timer timer= null;
    private int num=0;//可以此次对话之前有没有对话
    public Qianfan getBase() {
        return base;
    }

    @Override
    public String chat(Config message) {
        ChatConfig message1 = (ChatConfig)message;
        String result = null;
        //选择是单论对话还是多轮对话
        if(message1.getType().equals("one")){//根据信息筛选到底进行单论对话还是多轮对话
            System.out.println("开展单轮对话");
            result =  chatOne(message1);//单
        }else {
            System.out.println("开展多轮对话");
            if(num==0) {//如果是第一次发言就开始进入计时器，等到一定时间之后还没有人发言就终止计时,这里有个缺陷就是对方正好卡在二十秒的时候给了个反馈
                System.out.println("多轮对话第一次提问");
                timer = new Timer(arrayList,this);
                new Thread(timer).start();
                num++;
            }else {
                timer.setI(1);
            }
            result = chatMore(message1);//多
        }
        System.out.println(result);
        return result;
    }

    //调用多轮对话
    private String chatMore(ChatConfig message) {
        StringBuilder sb= new StringBuilder();

        //首先存入要提问的问题
        arrayList.add(new Message().setRole("user").setContent(message.getMessage()));
        for (Message message1 : arrayList) {
            System.out.println(message1.getRole()+message1.getContent());
        }
        //然后流式获取信息
        base.chatCompletion().model(message.getModel())
                .topP(message.getTop_P())
                .temperature(message.getTemperature())
                .messages(arrayList)
                .executeStream()
                .forEachRemaining(v -> {
                    sb.append(v.getResult().toString());
                    System.out.println(v.getResult().toString());
                });
        //获取信息结束后把信息填入list
        arrayList.add(new Message().setRole("assistant").setContent(sb.toString()));
        return sb.toString();
    }

    //调用一个单论对话
    public String chatOne(ChatConfig message){
        final String[] result = {null};
        ChatBuilder builder = base.chatCompletion().model(message.getModel());
        builder.addMessage("user",message.getMessage())
                .temperature(message.getTemperature())
                .topP(message.getTop_P())
                .executeStream().forEachRemaining(v->{
                    result[0] = v.getResult().toString();
                    System.out.println(result[0]);
                });
        return result[0];
    }








    public void setBase(Qianfan base) {
        this.base = base;
    }

    public ArrayList<Message> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Message> arrayList) {
        this.arrayList = arrayList;
    }

    public Timer getTimer() {
        return timer;
    }

    public void setTimer(Timer timer) {
        this.timer = timer;
    }


    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

}
