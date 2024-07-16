package com.example.ai.languageModel;

import com.example.ai.languageModel.entity.Config;
import com.example.ai.languageModel.entity.Message;
import com.example.ai.languageModel.entity.Observer;
import com.example.ai.languageModel.entity.Question;

public interface AI {
    Message chat(Config conversationConfig, Question question, Observer observer);
    void pause(String taskID,String username);
}
