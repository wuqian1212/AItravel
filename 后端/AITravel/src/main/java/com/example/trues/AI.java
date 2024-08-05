package com.example.trues;

import com.example.trues.languageModel.entity.Config;
import com.example.trues.languageModel.entity.Message;
import com.example.trues.languageModel.entity.Observer;
import com.example.trues.languageModel.entity.Question;

public interface AI {
    Message callVoice(Config conversationConfig, Question question, String mp3Dir, Observer o);
    Message callText(Config conversationConfig, Question question,Observer o);//这样调用的话直接流式返回

}
