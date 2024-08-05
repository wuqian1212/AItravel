package com.example.trues.languageModel.AIimpl;

import com.example.trues.AI;
import com.example.trues.entity.APIResult;
import com.example.trues.entity.SenseVoiceResult;
import com.example.trues.exceptions.VoiceExcepiton;
import com.example.trues.languageModel.entity.Config;
import com.example.trues.languageModel.entity.Message;
import com.example.trues.languageModel.entity.Observer;
import com.example.trues.languageModel.entity.Question;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ClaudeAI implements AI {
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
            url= new URL("http://localhost:8080/API_war_exploded/audio-text");
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
                System.out.println(text);
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
        return null;
    }

}
