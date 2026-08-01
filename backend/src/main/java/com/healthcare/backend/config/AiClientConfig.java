package com.healthcare.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

import java.time.Duration;

@Configuration
public class AiClientConfig {

    @Bean
    public RestClient aiTriageRestClient(
            @Value("${ai.triage.base-url}") String baseUrl,
            @Value("${ai.triage.connect-timeout-seconds:3}")
            long connectTimeout,
            @Value("${ai.triage.read-timeout-seconds:5}")
            long readTimeout
    ) {
        SimpleClientHttpRequestFactory requestFactory =
                new SimpleClientHttpRequestFactory();

        requestFactory.setConnectTimeout(
                Duration.ofSeconds(connectTimeout)
        );

        requestFactory.setReadTimeout(
                Duration.ofSeconds(readTimeout)
        );

        return RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(requestFactory)
                .build();
    }
}