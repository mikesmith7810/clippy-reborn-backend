package com.clippy.handler;

import com.clippy.model.MessageType;

public interface ChatRequestHandler {

    MessageType getType();

    String handle(String message);
}
