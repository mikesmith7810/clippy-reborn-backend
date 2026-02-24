package com.clippy.service;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String chat(String userMessage, String calendarContext) {
        String template = """
                You are Clippy, a helpful desktop assistant.

                Here are the user's calendar events for today:
                {calendarContext}

                User: {userMessage}
                Assistant:
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(
                java.util.Map.of(
                        "calendarContext", calendarContext,
                        "userMessage", userMessage
                )
        );

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
