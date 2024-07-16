package com.travel.aitravel.controller;


import com.travel.aitravel.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/GPT")
public class GPT {
    @Autowired
    private ChatService chatService;

    //获取request中的参数并返回响应回答
    @RequestMapping("/chat")
    public String chat(String question) {
        return chatService.chat(question);
    }

}
