package com.clippy.handler;

import com.clippy.model.MessageType;
import org.springframework.stereotype.Component;

@Component
public class DefaultRequestHandler implements ChatRequestHandler {

    @Override
    public MessageType getType() {
        return MessageType.UNKNOWN;
    }

    @Override
    public String handle(String message) {
        return "I'm not set up to handle that type of request yet.";
    }
}
