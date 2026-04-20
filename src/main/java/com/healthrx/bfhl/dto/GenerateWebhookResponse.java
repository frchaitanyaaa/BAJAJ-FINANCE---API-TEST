package com.healthrx.bfhl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GenerateWebhookResponse(String webhook, String accessToken) {
}
