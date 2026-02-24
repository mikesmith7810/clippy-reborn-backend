package com.clippy.service;

import com.clippy.model.DateRange;
import com.clippy.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;

@Service
public class ChatService {

    private static final Logger log = LoggerFactory.getLogger(ChatService.class);

    private final ChatModel chatModel;

    public ChatService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public MessageType classify(String userMessage) {
        String template = """
                Classify the following user message into exactly one of these categories:
                CALENDAR - questions about calendar events, schedule, meetings, appointments, availability, or anything date/time related. Today means events for today and tomorrow means events for tomorrow.
                SLACK - questions about Slack messages, channel activity, recent posts, or what people are saying.
                UNKNOWN - anything else.

                Respond with only the category name and nothing else.

                Message: {userMessage}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of("userMessage", userMessage));
        String response = chatModel.call(prompt).getResult().getOutput().getText().trim().toUpperCase();

        try {
            return MessageType.valueOf(response);
        } catch (IllegalArgumentException e) {
            // LLM may pad the response with punctuation or extra words — scan for a known type
            for (MessageType type : MessageType.values()) {
                if (type != MessageType.UNKNOWN && response.contains(type.name())) {
                    return type;
                }
            }
            log.warn("Could not classify message, raw LLM response was: '{}'", response);
            return MessageType.UNKNOWN;
        }
    }

    public String extractChannelName(String userMessage) {
        String template = """
                Extract the Slack channel name from the following message.
                Respond with only the channel name, without the # symbol (e.g. "general", "engineering").
                If no specific channel is mentioned, respond with "NONE".

                Message: {userMessage}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of("userMessage", userMessage));
        String response = chatModel.call(prompt).getResult().getOutput().getText().trim();

        if (response.isBlank() || response.equalsIgnoreCase("NONE")) {
            return null;
        }
        return response.toLowerCase().replaceAll("[^a-z0-9_-]", "");
    }

    public DateRange extractDateRange(String userMessage) {
        String lower = userMessage.toLowerCase();
        LocalDate today = LocalDate.now();

        if (lower.contains("today")) return DateRange.today();

        if (lower.contains("tomorrow")) {
            LocalDate tomorrow = today.plusDays(1);
            return new DateRange(tomorrow, tomorrow);
        }

        if (lower.contains("next week")) {
            LocalDate nextMonday = today.with(DayOfWeek.MONDAY).plusWeeks(1);
            return new DateRange(nextMonday, nextMonday.plusDays(6));
        }

        if (lower.contains("this week")) {
            return new DateRange(today.with(DayOfWeek.MONDAY), today.with(DayOfWeek.SUNDAY));
        }

        for (DayOfWeek dow : DayOfWeek.values()) {
            if (lower.contains(dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH).toLowerCase())) {
                LocalDate date = today.with(dow);
                return new DateRange(date, date);
            }
        }

        log.warn("Could not determine date range for '{}', defaulting to today", userMessage);
        return DateRange.today();
    }

    public String chat(String userMessage, String context) {
        String template = """
                You are Clippy, a helpful desktop assistant.
                Today is {today}.

                Here is some context to help answer the user's question:
                {context}

                User: {userMessage}
                Assistant:
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of(
                "today", LocalDate.now().toString(),
                "context", context,
                "userMessage", userMessage
        ));

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
