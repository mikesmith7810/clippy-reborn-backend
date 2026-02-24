package com.clippy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "slack")
public class SlackConfig {

    private String botToken;
    private int messageLimit;
    private Map<String, String> channelIds;

    public String getBotToken() { return botToken; }
    public void setBotToken(String botToken) { this.botToken = botToken; }

    public int getMessageLimit() { return messageLimit; }
    public void setMessageLimit(int messageLimit) { this.messageLimit = messageLimit; }

    public Map<String, String> getChannelIds() { return channelIds; }
    public void setChannelIds(Map<String, String> channelIds) { this.channelIds = channelIds; }
}
