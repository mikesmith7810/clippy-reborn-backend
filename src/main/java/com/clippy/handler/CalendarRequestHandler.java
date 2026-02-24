package com.clippy.handler;

import com.clippy.model.MessageType;
import com.clippy.service.CalendarService;
import com.clippy.service.ChatService;
import org.springframework.stereotype.Component;

@Component
public class CalendarRequestHandler implements ChatRequestHandler {

    private final CalendarService calendarService;
    private final ChatService chatService;

    public CalendarRequestHandler(CalendarService calendarService, ChatService chatService) {
        this.calendarService = calendarService;
        this.chatService = chatService;
    }

    @Override
    public MessageType getType() {
        return MessageType.CALENDAR;
    }

    @Override
    public String handle(String message) {
        String calendarContext = calendarService.getEvents(chatService.extractDateRange(message));
        return chatService.chat(message, calendarContext);
    }
}
