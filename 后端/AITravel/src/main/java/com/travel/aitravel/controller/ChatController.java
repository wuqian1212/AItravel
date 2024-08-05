package com.travel.aitravel.controller;

import com.travel.aitravel.R;
import com.travel.aitravel.dto.ChatRequest;
import com.travel.aitravel.dto.PhotoInfo;
import com.travel.aitravel.dto.VoiceInfo;
import com.travel.aitravel.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 控制器类，处理与聊天相关的HTTP请求。
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;


    @PostMapping("/message")
    public SseEmitter chat(@RequestBody ChatRequest chatRequest) {
        SseEmitter emitter = new SseEmitter();
        chatService.chat(chatRequest, emitter);
        return emitter;
    }

    @PostMapping("/photo")
    public SseEmitter photo(@RequestBody PhotoInfo photoInfo) {
        SseEmitter emitter = new SseEmitter();
        chatService.photo(photoInfo, emitter);
        return emitter;
    }

    @PostMapping("/voice")
    public SseEmitter voice(@RequestBody VoiceInfo voiceInfo) {
        SseEmitter emitter = new SseEmitter();
        chatService.voice(voiceInfo, emitter);
        return emitter;
    }

}
