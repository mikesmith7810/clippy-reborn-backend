package com.clippy.service;

import com.clippy.config.SlackConfig;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.response.conversations.ConversationsHistoryResponse;
import com.slack.api.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SlackService {

    private static final Logger log = LoggerFactory.getLogger(SlackService.class);

    private final SlackConfig slackConfig;

    public SlackService(SlackConfig slackConfig) {
        this.slackConfig = slackConfig;
    }

    public String getRecentMessages(String channelName) {
        Map<String, String> channelIds = slackConfig.getChannelIds();
        String channelId = channelIds != null ? channelIds.get(channelName) : null;

        String prefix = null;
        if (channelId == null) {
            if (channelIds == null || channelIds.isEmpty()) {
                return "No Slack channels are configured.";
            }
            Map.Entry<String, String> fallback = channelIds.entrySet().iterator().next();
            log.warn("Channel '{}' not found in config, falling back to '{}'", channelName, fallback.getKey());
            prefix = (channelName != null ? "I couldn't find #" + channelName + ", but " : "")
                    + "here's what's going on in #" + fallback.getKey() + ":\n";
            channelName = fallback.getKey();
            channelId = fallback.getValue();
        }

        final String resolvedChannelId = channelId;
        final String resolvedChannelName = channelName;

        try (Slack slack = Slack.getInstance()) {
            MethodsClient methods = slack.methods(slackConfig.getBotToken());

            ConversationsHistoryResponse response = methods.conversationsHistory(r -> r
                    .channel(resolvedChannelId)
                    .limit(slackConfig.getMessageLimit()));

            if (!response.isOk()) {
                log.warn("conversations.history failed for #{}: {}", resolvedChannelName, response.getError());
                return "Could not retrieve messages from #" + resolvedChannelName + ".";
            }

            StringBuilder result = new StringBuilder();
            if (prefix != null) result.append(prefix);
            result.append("#").append(resolvedChannelName).append(":\n");
            for (Message message : response.getMessages()) {
                String user = message.getUser() != null ? message.getUser() : "unknown";
                result.append("- ").append(user).append(": ").append(message.getText()).append("\n");
            }

            return result.toString();
        } catch (Exception e) {
            log.error("Failed to retrieve Slack messages", e);
            return "Could not retrieve Slack messages.";
        }
    }
}
