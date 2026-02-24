package com.clippy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "google.calendar")
public class GoogleCalendarConfig {

    private String credentialsFile;
    private String tokensDir;
    private String user;

    public String getCredentialsFile() { return credentialsFile; }
    public void setCredentialsFile(String credentialsFile) { this.credentialsFile = credentialsFile; }

    public String getTokensDir() { return tokensDir; }
    public void setTokensDir(String tokensDir) { this.tokensDir = tokensDir; }

    public String getUser() { return user; }
    public void setUser(String user) { this.user = user; }
}
