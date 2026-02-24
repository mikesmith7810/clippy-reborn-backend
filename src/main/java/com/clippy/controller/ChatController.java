package com.clippy.controller;

import com.clippy.service.ChatMessageRouter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatMessageRouter router;

    public ChatController(ChatMessageRouter router) {
        this.router = router;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> body) {
        String reply = router.route(body.get("message"));
        return Map.of("reply", reply);
    }
}
