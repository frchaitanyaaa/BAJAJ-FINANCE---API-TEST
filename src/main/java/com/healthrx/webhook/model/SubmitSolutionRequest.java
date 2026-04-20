package com.healthrx.webhook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SubmitSolutionRequest(
        @JsonProperty("finalQuery") String finalQuery
) {
}
