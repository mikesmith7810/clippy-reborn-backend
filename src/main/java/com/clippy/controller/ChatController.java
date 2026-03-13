package com.clippy.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    String reply = chatMessageRouter.route(body.get("message"));
    return Map.of("reply", reply);
  }
}
