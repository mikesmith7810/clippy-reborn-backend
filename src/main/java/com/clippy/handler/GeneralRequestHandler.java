package com.clippy.handler;

import com.clippy.model.MessageType;
import com.clippy.service.ChatService;
import org.springframework.stereotype.Component;

@Component
public class GeneralRequestHandler implements ChatRequestHandler {

  private final ChatService chatService;

  public GeneralRequestHandler(ChatService chatService) {
    this.chatService = chatService;
  }

  @Override
  public MessageType getType() {
    return MessageType.GENERAL;
  }

  @Override
  public String handle(String message) {
    return chatService.chatGeneral(message);
  }
}
