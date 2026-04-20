package com.healthrx.bfhl.service;

import com.healthrx.bfhl.config.AppProperties;
import com.healthrx.bfhl.dto.GenerateWebhookRequest;
import com.healthrx.bfhl.dto.GenerateWebhookResponse;
import com.healthrx.bfhl.dto.SubmitSolutionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WebhookClient {

    private static final Logger log = LoggerFactory.getLogger(WebhookClient.class);

    private final RestTemplate restTemplate;
    private final AppProperties appProperties;

    public WebhookClient(RestTemplate restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate;
        this.appProperties = appProperties;
    }

    public GenerateWebhookResponse generateWebhook(GenerateWebhookRequest request) {
        log.info("Requesting webhook generation for regNo={}", request.regNo());

        ResponseEntity<GenerateWebhookResponse> response = restTemplate.postForEntity(
                appProperties.getApi().getGenerateWebhookUrl(),
                request,
                GenerateWebhookResponse.class
        );

        GenerateWebhookResponse body = response.getBody();
        if (body == null || body.webhook() == null || body.accessToken() == null) {
            throw new IllegalStateException("Invalid generateWebhook response: missing webhook or accessToken.");
        }

        return body;
    }

    public void submitSolution(String webhookUrl, String accessToken, String finalQuery) {
        log.info("Submitting SQL solution to webhook URL.");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set(HttpHeaders.AUTHORIZATION, accessToken);

        HttpEntity<SubmitSolutionRequest> requestEntity = new HttpEntity<>(
                new SubmitSolutionRequest(finalQuery),
                headers
        );

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        log.info("Submission completed with status={} and body={}", response.getStatusCode(), response.getBody());
    }
}
