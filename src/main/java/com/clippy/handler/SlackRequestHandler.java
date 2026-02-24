package com.clippy.handler;

import com.clippy.model.MessageType;
import com.clippy.service.ChatService;
import com.clippy.service.SlackService;
import org.springframework.stereotype.Component;

@Component
public class SlackRequestHandler implements ChatRequestHandler {

    private final SlackService slackService;
    private final ChatService chatService;

    public SlackRequestHandler(SlackService slackService, ChatService chatService) {
        this.slackService = slackService;
        this.chatService = chatService;
    }

    @Override
    public MessageType getType() {
        return MessageType.SLACK;
    }

    @Override
    public String handle(String message) {
        String channelName = chatService.extractChannelName(message);
        String slackContext = slackService.getRecentMessages(channelName);
        return chatService.chat(message, slackContext);
    }
}
