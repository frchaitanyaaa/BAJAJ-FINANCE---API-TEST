package com.healthrx.webhook;

import com.healthrx.webhook.config.ChallengeProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ChallengeProperties.class)
public class WebhookSqlSolverApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebhookSqlSolverApplication.class, args);
    }
}
