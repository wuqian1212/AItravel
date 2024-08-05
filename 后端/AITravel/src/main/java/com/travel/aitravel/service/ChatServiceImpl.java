package com.travel.aitravel.service;

import com.example.trues.languageModel.AIimpl.DifyAI;
import com.example.trues.languageModel.entity.Config;
import com.example.trues.languageModel.entity.Observer;
import com.example.trues.languageModel.entity.Question;
import com.travel.aitravel.dao.UserInfo;
import com.travel.aitravel.dto.ChatRequest;
import com.travel.aitravel.dto.PhotoInfo;
import com.travel.aitravel.dto.VoiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 实现聊天服务的类，提供与用户交互的功能，包括文本聊天、图片上传和语音上传。
 */
@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private UserInfoService userInfoService;

    @Override
    public void chat(ChatRequest chatRequest, SseEmitter emitter) {
        DifyAI chatAI = new DifyAI("app-JTraL0zj8wbWFaoONP6NzpGi");
        Observer observer = new Observer(emitter);

        Config config = new Config();
        config.setAfterIp(chatRequest.getAfterIp());
        config.setUsername(chatRequest.getUsername());
        config.setConversationID(chatRequest.getConversationID());

        Question question = new Question();
        question.setContent(chatRequest.getContent());
        int chatRequestId = chatRequest.getId();
        UserInfo userInfo = userInfoService.getUserInfoById(chatRequestId);
        String prompt = "{\"sex\":\"" + userInfo.getSex() + "\",\"age\":\"" + userInfo.getAge() + "\",\"career\":\"" + userInfo.getCareer() + "\",\"likes\":\"" + userInfo.getLikes() + "\"}";
        question.setPrompt(prompt);

        new Thread(() -> {
            try {
                chatAI.callText(config, question, observer);
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data("Error: " + e.getMessage()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }).start();
    }

    @Override
    public void photo(PhotoInfo photoInfo, SseEmitter emitter) {
        byte[] image = photoInfo.getPhoto();
        String fileName = "img/received_image.jpg";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(image);
            emitter.send(SseEmitter.event().name("photoUpload").data("Image uploaded successfully"));
        } catch (IOException e) {
            try {
                emitter.send(SseEmitter.event().name("error").data("Error saving image: " + e.getMessage()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public void voice(VoiceInfo voiceInfo, SseEmitter emitter) {
        byte[] voice = voiceInfo.getVoice();
        String fileName = "voice/received_voice.wav";
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(voice);
            emitter.send(SseEmitter.event().name("voiceUpload").data("Voice uploaded successfully"));
        } catch (IOException e) {
            try {
                emitter.send(SseEmitter.event().name("error").data("Error saving voice: " + e.getMessage()));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            return;
        }

        DifyAI chatAI = new DifyAI("app-JTraL0zj8wbWFaoONP6NzpGi");
        Observer observer = new Observer(emitter);

        Config config = new Config();
        config.setAfterIp(voiceInfo.getAfterIp());
        config.setUsername(voiceInfo.getUsername());
        config.setConversationID(voiceInfo.getConversationID());

        Question question = new Question();
        int chatRequestId = voiceInfo.getId();
        UserInfo userInfo = userInfoService.getUserInfoById(chatRequestId);
        String prompt = "{\"sex\":\"" + userInfo.getSex() + "\",\"age\":\"" + userInfo.getAge() + "\",\"career\":\"" + userInfo.getCareer() + "\",\"likes\":\"" + userInfo.getLikes() + "\"}";
        question.setPrompt(prompt);

        new Thread(() -> {
            try {
                chatAI.callVoice(config, question, fileName, observer);
            } catch (Exception e) {
                try {
                    emitter.send(SseEmitter.event().name("error").data("Error: " + e.getMessage()));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();
    }
}
