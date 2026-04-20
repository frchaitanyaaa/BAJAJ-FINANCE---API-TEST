package com.healthrx.webhook.model;

public record GenerateWebhookRequest(
        String name,
        String regNo,
        String email
) {
}
