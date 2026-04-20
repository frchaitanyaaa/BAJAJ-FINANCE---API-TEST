package com.healthrx.bfhl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Candidate candidate = new Candidate();
    private Api api = new Api();

    public Candidate getCandidate() {
        return candidate;
    }

    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    public static class Candidate {
        private String name = "John Doe";
        private String regNo = "REG12347";
        private String email = "john@example.com";

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
    }

    public static class Api {
        private String generateWebhookUrl = "https://bfhldevapigw.healthrx.co.in/hiring/generateWebhook/JAVA";

        public String getGenerateWebhookUrl() {
            return generateWebhookUrl;
        }

        public void setGenerateWebhookUrl(String generateWebhookUrl) {
            this.generateWebhookUrl = generateWebhookUrl;
        }
    }
}
