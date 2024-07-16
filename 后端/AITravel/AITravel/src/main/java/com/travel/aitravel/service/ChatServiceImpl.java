package com.travel.aitravel.service;

import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.constant.SparkApiVersion;
import io.github.briqt.spark4j.exception.SparkException;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.SparkSyncChatResponse;
import io.github.briqt.spark4j.model.request.SparkRequest;
import io.github.briqt.spark4j.model.response.SparkTextUsage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class ChatServiceImpl implements ChatService{

    private SparkClient sparkClient;
    private List<SparkMessage> messages;

    public ChatServiceImpl() {
        this.sparkClient = new SparkClient();
        // 设置认证信息
        sparkClient.appid = "b32dced2";
        sparkClient.apiKey = "5e7f0132b058c628ec2d26769c187a65";
        sparkClient.apiSecret = "OWI1YzdjOTY3ODVkMDcxNjEyMGFkOGNm";
        this.messages = new ArrayList<>();
    }
    @Override
    public String chat(String question) {
        messages.add(SparkMessage.userContent(question));
        SparkRequest sparkRequest = SparkRequest.builder()
                // 消息列表
                .messages(messages)
                .maxTokens(2048)
                .temperature(0.2)
                .apiVersion(SparkApiVersion.V3_0)
                .build();

        try {
            // 同步调用
            SparkSyncChatResponse chatResponse = sparkClient.chatSync(sparkRequest);
            SparkTextUsage textUsage = chatResponse.getTextUsage();

            String response = chatResponse.getContent();
            System.out.println("\n提问tokens：" + textUsage.getPromptTokens()
                    + "，回答tokens：" + textUsage.getCompletionTokens()
                    + "，总消耗tokens：" + textUsage.getTotalTokens());
            return response;
        } catch (SparkException e) {
            System.out.println("发生异常了：" + e.getMessage());
            return "发生异常了：" + e.getMessage();
        }
    }

}
