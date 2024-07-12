package com.example.ai.languageModel.AIimpl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.ai.languageModel.*;
import com.example.ai.languageModel.entity.Config;
import com.example.ai.languageModel.entity.Message;
import com.example.ai.languageModel.entity.Observer;
import com.example.ai.languageModel.entity.Question;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PauseAI implements AI {
    @Override
    public Message chat(Config conversationConfig, Question question, Observer observer) {
        return null;
    }

    @Override
    public void pause(String taskID, String username) {
        try {
            URL url = new URL("http://192.168.1.152/v1/chat-messages/" + taskID + "/stop");
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setRequestMethod("POST");
            huc.setRequestProperty("Authorization", "Bearer app-JWAkQ31jGQvhefd2LmNnsJRS");
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
