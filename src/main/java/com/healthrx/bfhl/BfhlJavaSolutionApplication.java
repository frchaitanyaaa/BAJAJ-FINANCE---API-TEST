package com.healthrx.bfhl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class BfhlJavaSolutionApplication {

    public static void main(String[] args) {
        SpringApplication.run(BfhlJavaSolutionApplication.class, args);
    }
}
