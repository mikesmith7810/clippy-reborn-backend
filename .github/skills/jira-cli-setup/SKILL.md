---
name: jira-cli-setup
description: This skill ensures that the jira-cli tool is installed and configured correctly for interacting with Jira for task management and issue tracking.
user-invocable: false
---

## Overview

This skill guides you through setting up and configuring JiraCLI, an interactive command-line tool for Atlassian Jira that streamlines issue management and workflow automation.

## What you'll learn

- Install JiraCLI on your system
- Configure authentication for Jira Cloud or on-premise installations
- Set up shell environment variables and configuration files
- Enable shell completion for improved usability

## Prerequisites

- Access to a Jira instance (Cloud or on-premise)
- A command-line terminal
- Git (for cloning repositories if needed)

## Installation steps

### 1. Download and install JiraCLI

Visit the [JiraCLI releases page](https://github.com/ankitpokhrel/jira-cli/releases) and download the binary for your platform (Linux, macOS, FreeBSD, NetBSD, or Windows).

Alternatively, use your package manager:
- **Homebrew**: `brew install jira-cli`
- **Docker**: `docker run -it --rm ghcr.io/ankitpokhrel/jira-cli:latest`

### 2. Configure authentication

**For Jira Cloud:**
- Generate an API token from your Jira account
- Export it: `export JIRA_API_TOKEN=your_token`
- Add to `$HOME/.bashrc` or your shell config for persistence

**For on-premise Jira:**
- For basic auth: `export JIRA_API_TOKEN=your_password`
- For PAT: `export JIRA_API_TOKEN=your_token` and `export JIRA_AUTH_TYPE=bearer`
- For mTLS: Use client certificates during `jira init`

### 3. Initialize configuration

Run `jira init` and follow the prompts to:
- Select your installation type (Cloud or Local)
- Provide your Jira server details
- Choose authentication method
- Generate your configuration file

### 4. Enable shell completion (optional)

Run `jira completion --help` to set up bash/zsh shell completion for faster command access.

## Using multiple project configurations

Load different configurations with: