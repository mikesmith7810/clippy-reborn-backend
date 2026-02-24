package com.clippy.service;

import com.clippy.model.DateRange;
import com.clippy.model.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
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

    public DateRange extractDateRange(String userMessage) {
        String template = """
                Today's date is {today}.
                Given the user message below, determine the date range they are asking about.
                Respond with ONLY two ISO dates (yyyy-MM-dd) in exactly this format:
                START=yyyy-MM-dd,END=yyyy-MM-dd

                Rules:
                - "today" → same date for start and end
                - "tomorrow" → next day for start and end
                - "this week" → Monday to Sunday of the current week
                - "next week" → Monday to Sunday of next week
                - A specific date → that date for start and end
                - If unclear, default to today

                Message: {userMessage}
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of(
                "today", LocalDate.now().toString(),
                "userMessage", userMessage
        ));

        String response = chatModel.call(prompt).getResult().getOutput().getText().trim();

        try {
            String[] parts = response.split(",");
            LocalDate start = LocalDate.parse(parts[0].replace("START=", "").trim());
            LocalDate end = LocalDate.parse(parts[1].replace("END=", "").trim());
            return new DateRange(start, end);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            log.warn("Failed to parse date range from LLM response '{}', defaulting to today", response);
            return DateRange.today();
        }
    }

    public String chat(String userMessage, String calendarContext) {
        String template = """
                You are Clippy, a helpful desktop assistant.
                Today's date is {today}.

                Here are the user's calendar events:
                {calendarContext}

                User: {userMessage}
                Assistant:
                """;

        PromptTemplate promptTemplate = new PromptTemplate(template);
        Prompt prompt = promptTemplate.create(Map.of(
                "today", LocalDate.now().toString(),
                "calendarContext", calendarContext,
                "userMessage", userMessage
        ));

        return chatModel.call(prompt).getResult().getOutput().getText();
    }
}
