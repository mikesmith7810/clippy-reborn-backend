package com.clippy.controller;

import com.clippy.service.CalendarService;
import com.clippy.service.ChatService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ChatController {

    private final ChatService chatService;
    private final CalendarService calendarService;

    public ChatController(ChatService chatService, CalendarService calendarService) {
        this.chatService = chatService;
        this.calendarService = calendarService;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String calendarContext = calendarService.getTodaysEvents();
        String reply = chatService.chat(message, calendarContext);
        return Map.of("reply", reply);
    }
}
