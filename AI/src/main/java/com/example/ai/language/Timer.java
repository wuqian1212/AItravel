package com.example.ai.language;

import com.baidubce.qianfan.model.chat.Message;
import com.example.ai.language.impl.ChatAI;

import java.util.ArrayList;

public class Timer implements Runnable{
    private Integer i=0;
    private ArrayList<Message> arrayList;
    ChatAI ai = new ChatAI();

    public Timer(ArrayList<Message> arrayList,ChatAI ai) {
        this.ai = ai;
        this.arrayList = arrayList;
    }

    public void setI(Integer i) {
        this.i = i;
    }

    public ArrayList<Message> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<Message> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public void run() {
        //如果计时发现在一定的时长之内没有新的内容，那么就把list置空，访问次数置零
        while(true){
            try {
                i=0;//让i=0如果有人在六十秒内更改了就不会结束，如果没有就会结束
                Thread.sleep(60000);
                if(i==0){
                    System.out.println("六十秒内没有进行操作");
                    arrayList.clear();
                    ai.setNum(0);
                    break;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }
}
