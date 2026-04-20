package com.healthrx.webhook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GenerateWebhookResponse(
        String webhook,
        @JsonProperty("accessToken") String accessToken
) {
}
