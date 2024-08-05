package com.travel.aitravel.service;

import com.travel.aitravel.dto.ChatRequest;
import com.travel.aitravel.dto.PhotoInfo;
import com.travel.aitravel.dto.VoiceInfo;
import okhttp3.WebSocket;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.socket.WebSocketSession;

public interface ChatService {

    public void chat(ChatRequest chatRequest, SseEmitter emitter);
    public void photo( PhotoInfo photoInfo, SseEmitter emitter);
    public void voice( VoiceInfo voiceInfo, SseEmitter emitter);

}
