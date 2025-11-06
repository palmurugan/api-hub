package com.serviq.api.config;

import com.serviq.api.config.properties.MicroserviceProperties;
import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final MicroserviceProperties microserviceProperties;

    @Bean
    public Map<String, WebClient> webClients(WebClient.Builder webClientBuilder) {
        Map<String, WebClient> webClients = new HashMap<>();

        microserviceProperties.getServices().forEach((serviceName, config) -> {
            log.info("Configuring WebClient for service: {} with baseUrl: {}",
                    serviceName, config.getBaseUrl());

            WebClient webClient = createWebClient(webClientBuilder, config);
            webClients.put(serviceName, webClient);
        });
        return webClients;
    }

    private WebClient createWebClient(WebClient.Builder webClientBuilder,
                                      MicroserviceProperties.ServiceConfig config) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, config.getConnectionTimeout())
                .responseTimeout(Duration.ofMillis(config.getResponseTimeout()))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(
                                        config.getReadTimeout(), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(
                                        config.getWriteTimeout(), TimeUnit.MILLISECONDS)));

        return webClientBuilder
                .baseUrl(config.getBaseUrl())
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .build();
    }
}
