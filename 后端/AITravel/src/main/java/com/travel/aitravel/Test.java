package com.travel.aitravel;

import com.travel.aitravel.service.ChatServiceImpl;
import io.github.briqt.spark4j.SparkClient;
import io.github.briqt.spark4j.model.SparkMessage;
import io.github.briqt.spark4j.model.request.SparkRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        SparkClient sparkClient = new SparkClient();
        // 设置认证信息
        sparkClient.appid = "b32dced2";
        sparkClient.apiKey = "5e7f0132b058c628ec2d26769c187a65";
        sparkClient.apiSecret = "OWI1YzdjOTY3ODVkMDcxNjEyMGFkOGNm";

        // 消息列表，可以在此列表添加历史对话记录
        List<SparkMessage> messages=new ArrayList<>();
        messages.add(SparkMessage.userContent("请你扮演我的语文老师李老师，问我讲解问题问题，希望你可以保证知识准确，逻辑严谨。"));
        messages.add(SparkMessage.assistantContent("好的，这位同学，有什么问题需要李老师为你解答吗？"));
        messages.add(SparkMessage.userContent("鲁迅和周树人小时候打过架吗？"));

// 构造请求
        SparkRequest sparkRequest=SparkRequest.builder()
// 消息列表
                .messages(messages)
// 模型回答的tokens的最大长度,非必传，默认为2048。
// V1.5取值为[1,4096]
// V2.0取值为[1,8192]
// V3.0取值为[1,8192]
                .maxTokens(2048)
// 核采样阈值。用于决定结果随机性,取值越高随机性越强即相同的问题得到的不同答案的可能性越高 非必传,取值为[0,1],默认为0.5
                .temperature(0.2)
                .build();

        // 使用默认的控制台监听器，流式调用；
        // 实际使用时请继承SparkBaseListener自定义监听器实现
        sparkClient.chatStream(sparkRequest,new SparkConsoleListener());
    }
}
