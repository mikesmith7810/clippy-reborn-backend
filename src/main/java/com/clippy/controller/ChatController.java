package com.clippy.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import com.clippy.service.ChatMessageRouter;

@RestController
@RequestMapping("/api")
public class ChatController {

  private final ChatMessageRouter chatMessageRouter;

  public ChatController(ChatMessageRouter chatMessageRouter) {
    this.chatMessageRouter = chatMessageRouter;
  }

  @PostMapping("/chat")
  public Map<String, String> chat(@RequestBody Map<String, String> body) {
    if (body == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message is required");
    }

    String message = body.get("message");
    if (message == null || message.isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "message is required");
    }

    String reply = chatMessageRouter.route(message);
    return Map.of("reply", reply);
  }
}
