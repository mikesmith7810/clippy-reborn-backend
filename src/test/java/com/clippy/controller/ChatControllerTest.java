package com.clippy.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.clippy.service.ChatMessageRouter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ChatController.class)
class ChatControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private ChatMessageRouter chatMessageRouter;

  @Test
  void chatReturnsBadRequestWhenMessageIsNull() throws Exception {
    mockMvc
        .perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":null}"))
        .andExpect(status().isBadRequest());

    verify(chatMessageRouter, never()).route(anyString());
  }

  @Test
  void chatReturnsBadRequestWhenMessageIsMissing() throws Exception {
    mockMvc
        .perform(post("/api/chat").contentType(MediaType.APPLICATION_JSON).content("{}"))
        .andExpect(status().isBadRequest());

    verify(chatMessageRouter, never()).route(anyString());
  }

  @Test
  void chatReturnsBadRequestWhenMessageIsBlank() throws Exception {
    mockMvc
        .perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"   \"}"))
        .andExpect(status().isBadRequest());

    verify(chatMessageRouter, never()).route(anyString());
  }

  @Test
  void chatReturnsReplyForValidMessage() throws Exception {
    given(chatMessageRouter.route("hello")).willReturn("Hi!");

    mockMvc
        .perform(
            post("/api/chat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"message\":\"hello\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.reply").value("Hi!"));
  }
}
