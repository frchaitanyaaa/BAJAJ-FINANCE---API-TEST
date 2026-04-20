package com.healthrx.webhook.config;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "challenge")
public class ChallengeProperties {

    @NotBlank
    private String generateWebhookUrl;

    @NotBlank
    private String name;

    @NotBlank
    private String regNo;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String question1Sql;

    @NotBlank
    private String question2Sql;

    public String getGenerateWebhookUrl() {
        return generateWebhookUrl;
    }

    public void setGenerateWebhookUrl(String generateWebhookUrl) {
        this.generateWebhookUrl = generateWebhookUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQuestion1Sql() {
        return question1Sql;
    }

    public void setQuestion1Sql(String question1Sql) {
        this.question1Sql = question1Sql;
    }

    public String getQuestion2Sql() {
        return question2Sql;
    }

    public void setQuestion2Sql(String question2Sql) {
        this.question2Sql = question2Sql;
    }
}
