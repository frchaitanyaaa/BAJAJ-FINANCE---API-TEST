package com.healthrx.webhook.service;

import com.healthrx.webhook.config.ChallengeProperties;
import com.healthrx.webhook.model.GenerateWebhookRequest;
import com.healthrx.webhook.model.GenerateWebhookResponse;
import com.healthrx.webhook.model.SubmitSolutionRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

@Component
@ConditionalOnProperty(name = "challenge.enabled", havingValue = "true", matchIfMissing = true)
public class WebhookChallengeRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(WebhookChallengeRunner.class);

    private final RestTemplate restTemplate;
    private final ChallengeProperties properties;

    public WebhookChallengeRunner(RestTemplate restTemplate, ChallengeProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    @Override
    public void run(ApplicationArguments args) {
        GenerateWebhookResponse webhookResponse = generateWebhook();
        String finalQuery = chooseFinalQuery(properties.getRegNo());

        submitFinalQuery(webhookResponse.webhook(), webhookResponse.accessToken(), finalQuery);
        log.info("Challenge flow completed successfully.");
    }

    private GenerateWebhookResponse generateWebhook() {
        GenerateWebhookRequest request = new GenerateWebhookRequest(
                properties.getName(),
                properties.getRegNo(),
                properties.getEmail()
        );

        ResponseEntity<GenerateWebhookResponse> response = restTemplate.postForEntity(
                properties.getGenerateWebhookUrl(),
                request,
                GenerateWebhookResponse.class
        );

        GenerateWebhookResponse body = response.getBody();
        if (body == null || !StringUtils.hasText(body.webhook()) || !StringUtils.hasText(body.accessToken())) {
            throw new IllegalStateException("Invalid webhook generation response. Expected webhook and accessToken.");
        }

        log.info("Received webhook URL from challenge API.");
        return body;
    }

    private String chooseFinalQuery(String regNo) {
        String digitsOnly = regNo.replaceAll("\\D", "");
        if (digitsOnly.length() < 2) {
            throw new IllegalArgumentException("regNo must contain at least two digits to choose question set.");
        }

        int lastTwoDigits = Integer.parseInt(digitsOnly.substring(digitsOnly.length() - 2));
        boolean isOdd = lastTwoDigits % 2 != 0;
        String selectedQuery = isOdd ? properties.getQuestion1Sql() : properties.getQuestion2Sql();

        log.info("regNo={} -> lastTwoDigits={} -> selected Question {}", regNo, lastTwoDigits, isOdd ? "1" : "2");
        return selectedQuery;
    }

    private void submitFinalQuery(String webhookUrl, String accessToken, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(accessToken);

        SubmitSolutionRequest requestBody = new SubmitSolutionRequest(finalQuery);
        HttpEntity<SubmitSolutionRequest> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                webhookUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        log.info("Submission response status: {}", response.getStatusCode());
    }
}
