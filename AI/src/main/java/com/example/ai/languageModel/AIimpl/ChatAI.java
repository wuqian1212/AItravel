package com.example.ai.languageModel.AIimpl;

import cn.hutool.json.JSONObject;
import com.example.ai.languageModel.*;
import com.example.ai.languageModel.entity.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ChatAI implements AI {

    @Override
    public Message chat(Config conversationConfig, Question question, Observer observer) {
        //首先访问
        try {
            URL url = new URL("http://192.168.1.152/v1/"+conversationConfig.getAfterIp());
//            System.out.println(new String("AI".getBytes("UTF-8")));
//            URL url = new URL("https://api.dify.ai/v1/chat-messages");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization","Bearer app-JWAkQ31jGQvhefd2LmNnsJRS");
//            urlConnection.setRequestProperty("Authorization","Bearer app-5KzqtdvYWd9TbWOYeiIqCUGE");
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out =urlConnection.getOutputStream();
            String resquesContent=null;
            //判断是否是新会话
            if(conversationConfig.getConversationID().equals("")||conversationConfig.getConversationID()==null){
                System.out.println("新会话");
                     resquesContent = "{" +
                            "\"inputs\":"+question.getQu()+"," +
                            "\"query\":\""+question.getContent()+"\","+
                            "\"response_mode\":\"streaming\","+
                            "\"conversation_id\":\"\","+
                            "\"user\":\""+conversationConfig.getUsername()+"\","+
                            "\"files\":" +
                            "[]"+
                            "}";
            }else {
                System.out.println("旧会话");
                resquesContent = "{" +
                        "\"inputs\":"+question.getQu()+"," +
                        "\"query\":\""+question.getContent()+"\","+
                        "\"response_mode\":\"streaming\","+
                        "\"conversation_id\":\""+conversationConfig.getConversationID()+"\","+
                        "\"user\":\""+conversationConfig.getUsername()+"\","+
                        "\"files\":" +
                        "[]"+
                        "}";
            }
            //设置请求体
            out.write(resquesContent.getBytes());
            System.out.println(urlConnection.getResponseCode());
            out.flush();
            out.close();
            InputStream is = urlConnection.getInputStream();
             //获取信息
            return getMessageFromInputStream(is,observer);

        } catch (MalformedURLException e) {
            e.getMessage();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void pause(String taskID, String username) {

    }
    private static Message getMessageFromInputStream(InputStream is,Observer observer) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = null;
        String json = null;
        StringBuilder sb =new StringBuilder();
        String task_id = null;
        Message message = new Message();
        int i=1;
        while((content=br.readLine())!=null){
            //System.out.println(content);
            if(!content.equals("")) {//防止空行的返回
                json = content.substring(6);//获取到完整的json格式内部的内容
                String answerOne = null;
                JSONObject jo = new JSONObject(json);
                if(jo.get("answer")!=null) {
                    answerOne = jo.get("answer").toString();
                    sb.append(answerOne);
                    //System.out.print(answerOne);
                }
                if(i==1){
                    //开始设定返回值内容
                    task_id = jo.get("task_id").toString();
                    String conversion_id = jo.get("conversation_id").toString();
                    String message_id=jo.get("message_id").toString();
                    String time=jo.get("created_at").toString();
                    observer.setTaskID(task_id);
                    observer.pause();
                    message.setMessageID(message_id);
                    message.setTime(time);
                    message.setConversation(conversion_id);
                    message.setTaskID(task_id);
                }
                //这里要小心空指针异常
                ApartAnswer apartAnswer = new ApartAnswer(task_id,answerOne);
                observer.sendToWeb(apartAnswer);
                i++;

            }
        }
        System.out.println(sb.toString());
        return message;
    }

}
