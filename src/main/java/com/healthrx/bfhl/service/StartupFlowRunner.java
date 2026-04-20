package com.healthrx.bfhl.service;

import com.healthrx.bfhl.config.AppProperties;
import com.healthrx.bfhl.dto.GenerateWebhookRequest;
import com.healthrx.bfhl.dto.GenerateWebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupFlowRunner implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(StartupFlowRunner.class);

    private final AppProperties appProperties;
    private final WebhookClient webhookClient;
    private final SqlSolutionService sqlSolutionService;

    public StartupFlowRunner(AppProperties appProperties,
                             WebhookClient webhookClient,
                             SqlSolutionService sqlSolutionService) {
        this.appProperties = appProperties;
        this.webhookClient = webhookClient;
        this.sqlSolutionService = sqlSolutionService;
    }

    @Override
    public void run(ApplicationArguments args) {
        GenerateWebhookRequest request = new GenerateWebhookRequest(
                appProperties.getCandidate().getName(),
                appProperties.getCandidate().getRegNo(),
                appProperties.getCandidate().getEmail()
        );

        log.info("Starting webhook flow for {}", request.regNo());

        GenerateWebhookResponse generateWebhookResponse = webhookClient.generateWebhook(request);
        String finalQuery = sqlSolutionService.resolveFinalQuery(request.regNo());

        log.info("Resolved final SQL query:{}{}", System.lineSeparator(), finalQuery);

        webhookClient.submitSolution(
                generateWebhookResponse.webhook(),
                generateWebhookResponse.accessToken(),
                finalQuery
        );
    }
}
