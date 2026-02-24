package com.clippy;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
        "spring.ai.ollama.chat.model=llama3.2",
        "google.calendar.credentials-file=classpath:credentials.json",
        "google.calendar.tokens-dir=./tokens",
        "google.calendar.user=mike"
})
class ClippyBackendApplicationTests {

    @Test
    void contextLoads() {
    }
}
