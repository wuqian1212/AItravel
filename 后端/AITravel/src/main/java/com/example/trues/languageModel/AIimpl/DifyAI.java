package com.example.trues.languageModel.AIimpl;

import cn.hutool.json.JSONObject;
import com.example.trues.AI;
import com.example.trues.config.DifyConfig;
import com.example.trues.entity.APIResult;
import com.example.trues.entity.SenseVoiceResult;
import com.example.trues.exceptions.VoiceExcepiton;
import com.example.trues.languageModel.entity.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DifyAI implements AI {
    private String key;


    public DifyAI(String key) {
        this.key = key;
    }

    //首先conversionConfig暂时用不上，然后在question中判断是否需要tts
    @Override
    public Message callVoice(Config conversationConfig, Question question, String mp3Dir, Observer o) throws VoiceExcepiton{
        HttpURLConnection conn=null;
        InputStream inputStream = null;
        OutputStream outputStream = null;
        URL url = null;
        String resultJson;
        String text = null;
        try {
            System.out.println("开始执行");
            url= new URL(DifyConfig.audio2textURL);
            conn= (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            //设置文件
            inputStream= new FileInputStream(mp3Dir);
            outputStream= conn.getOutputStream();
            outputStream.write(inputStream.readAllBytes());
            resultJson = new String(conn.getInputStream().readAllBytes());
            Gson gson = new Gson();
            APIResult<SenseVoiceResult> result = gson.fromJson(resultJson,new TypeToken<APIResult<SenseVoiceResult>>(){}.getType());
            text = result.getData().getText();

            //判断结果是否可用
            if(result.getStatusCode()==300){
                throw new VoiceExcepiton("文件上传失败");
            }
            else if (result.getStatusCode()==301) {
                //如果失败就抛出异常说普通话不标准
                throw new VoiceExcepiton("听的不太清楚,请重新说一遍");
            }else {
                //System.out.println(text);
                //在这里把问题填入进去
                question.setContent(text);
                //如果成功就调用callText方法
                return this.callText(conversationConfig,question,o);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new VoiceExcepiton("与语音服务链接失败");
        } catch (IOException e) {
            e.printStackTrace();
            throw new VoiceExcepiton("输入输出失败");
        }finally {
            try {
                if(inputStream!=null) {
                    inputStream.close();
                }
                if (outputStream!=null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Message callText(Config conversationConfig, Question question, Observer o) {
        System.out.println(question.getContent());
        //在聊天中要进行判断，如果用户设置了需要语音回复那么就需要直接一整个获取完整之后交付给outVoice
        //如果不需要就直接流式从observer中反馈
        //首先访问
        try {
            //这里设定查询的本地dify的地址
            URL url = new URL(DifyConfig.URL +conversationConfig.getAfterIp());
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("POST");
            //设置请求头
            urlConnection.setRequestProperty("Authorization",DifyConfig.key+key);
            urlConnection.setRequestProperty("Content-Type","application/json");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            OutputStream out =urlConnection.getOutputStream();
            String resquesContent=null;
            //判断是否是新会话
            if(conversationConfig.getConversationID().equals("")||conversationConfig.getConversationID()==null){
                System.out.println("新会话");
                resquesContent = "{" +
                        "\"inputs\":"+question.getPrompt()+"," +
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
                        "\"inputs\":"+question.getPrompt()+"," +
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
            //取消打印状态码----------------------------------
            //System.out.println("与Dify交互获取到的状态码："+urlConnection.getResponseCode());
            out.flush();
            out.close();
            InputStream is = urlConnection.getInputStream();
            //获取结果的完整信息
            return getMessageFromInputStream(is,o,question.isGetTTS());
        } catch (MalformedURLException e) {
            e.getMessage();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
    private Message getMessageFromInputStream(InputStream is, Observer observer,boolean needTTS) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String content = null;
        String json = null;
        StringBuilder sb =new StringBuilder();
        String task_id = null;
        Message message = new Message();
        int i=1;
        //开始读取每一行的信息
        while((content=br.readLine())!=null){
            //取消打印完整的JSON格式--------------------------------
            //System.out.println(content);
            if(!content.equals("")) {//防止空行的返回
                json = content.substring(6);//获取到完整的json格式内部的内容
                if (!json.startsWith("{")){
                    //输出错误信息
                    System.out.println(json);
                    continue;
                }
                String answerOne = null;
                JSONObject jo = new JSONObject(json);
                if(jo.get("answer")!=null) {
                    answerOne = jo.get("answer").toString();
                    sb.append(answerOne);
                    //取消打印单次答案---------------------------------------------
                    //System.out.print(answerOne);
                }
                if(i==1){//在第一次的时候就设置好时间消息id会话id和任务id
                    //开始设定返回值内容
                    task_id = jo.get("task_id").toString();
                    String conversion_id = jo.get("conversation_id").toString();
                    String message_id=jo.get("message_id").toString();
                    String time=jo.get("created_at").toString();
                    observer.setTaskID(task_id);
//                    observer.pause();
                    //这里设定结果信息
                    message.setMessageID(message_id);
                    message.setTime(time);
                    message.setConversation(conversion_id);
                    message.setTaskID(task_id);
                }
                //这里要小心空指针异常
                ApartAnswer apartAnswer = new ApartAnswer(task_id,answerOne);
                if (needTTS){//如果需要语音就调用outVoice函数
                    if(apartAnswer.getApartAnswer()==null){//只有最后一次才发送
                        String outDir = text2Voice(sb.toString(),task_id);
                        System.out.println("sda");
                        observer.outVoiceToWeb(outDir);
                    }
                }else {
                    //使用观察者模式反馈，流式返回
                    observer.outTextToWeb(apartAnswer);
                    i++;
                }
            }
        }
        message.setAnswer(sb.toString());
        return message;
    }
    public String text2Voice(String text,String taskID) {
        String outPutDir = DifyConfig.mp3OutDirRoot+taskID+".wav";
        System.out.println("sad");
        String command1 = "python -c \"import sys; sys.path.append('E:\\CodeSpace\\AITravel');import barkToWav as a; a.textToMp3('"+outPutDir+"','"+text+"')";
        System.out.println(command1);
        Runtime console = Runtime.getRuntime();
        try {
            Process process = console.exec(command1);
            process.waitFor();
            return outPutDir;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void pause(String taskID, String username) {
        try {
            URL url = new URL(DifyConfig.URL + "chat-messages/"+taskID + "/stop");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setRequestProperty("Authorization", DifyConfig.key);
            huc.setRequestProperty("Content-Type", "application/json");
            huc.setDoInput(true);
            huc.setDoOutput(true);
            OutputStream out = huc.getOutputStream();

            String pauseRequestContent = "{" +
                    "\"user\":\"" + username + "\"" +
                    "}";

            out.write(pauseRequestContent.getBytes());
            out.flush();
            out.close();

            if (huc.getResponseCode() == 200) {
                System.out.println("Task paused successfully");
                InputStream is = huc.getInputStream();
                System.out.println(new BufferedReader(new InputStreamReader(is)).readLine());
//                String result = getResultFromInputStream(is);
//                System.out.println("Pause result: " + result);
            } else {
                System.out.println("Failed to pause task: " + huc.getResponseCode());
                System.out.println(new String(huc.getErrorStream().readNBytes(1000)));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
