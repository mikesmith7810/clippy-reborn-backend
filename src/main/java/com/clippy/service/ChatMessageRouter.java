package com.clippy.service;

import com.clippy.handler.ChatRequestHandler;
import com.clippy.handler.DefaultRequestHandler;
import com.clippy.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChatMessageRouter {

    private static final Logger log = LoggerFactory.getLogger(ChatMessageRouter.class);

    private final ChatService chatService;
    private final Map<MessageType, ChatRequestHandler> handlers;
    private final DefaultRequestHandler defaultHandler;

    public ChatMessageRouter(ChatService chatService,
                             List<ChatRequestHandler> handlers,
                             DefaultRequestHandler defaultHandler) {
        this.chatService = chatService;
        this.defaultHandler = defaultHandler;
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(ChatRequestHandler::getType, Function.identity()));
    }

    public String route(String message) {
        MessageType type = chatService.classify(message);
        log.info("Classified message as: {}", type);

        ChatRequestHandler handler = handlers.getOrDefault(type, defaultHandler);
        return handler.handle(message);
    }
}
