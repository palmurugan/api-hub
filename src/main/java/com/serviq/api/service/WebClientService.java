package com.serviq.api.service;

import com.serviq.api.config.properties.MicroserviceProperties;
import com.serviq.api.util.WebClientErrorHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebClientService {

    private final Map<String, WebClient> webClients;
    private final WebClientErrorHandler errorHandler;
    private final MicroserviceProperties microserviceProperties;

    public <T, R> Mono<R> post(String serviceName, String uri, T requestBody,
                               Class<R> responseType) {
        return post(serviceName, uri, requestBody, responseType, null);
    }

    public <T, R> Mono<R> post(String serviceName, String uri, T requestBody,
                               Class<R> responseType, Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestBodySpec request = getWebClient(serviceName)
                    .post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    public <T, R> Mono<R> post(String serviceName, String uri, T requestBody,
                               ParameterizedTypeReference<R> responseType) {
        return post(serviceName, uri, requestBody, responseType, null);
    }

    public <T, R> Mono<R> post(String serviceName, String uri, T requestBody,
                               ParameterizedTypeReference<R> responseType, Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestBodySpec request = getWebClient(serviceName)
                    .post()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    public <R> Mono<R> get(String serviceName, String uri, ParameterizedTypeReference<R> responseType) {
        return get(serviceName, uri, responseType, null);
    }

    public <R> Mono<R> get(String serviceName, String uri, ParameterizedTypeReference<R> responseType,
                           Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestHeadersSpec<?> request = getWebClient(serviceName)
                    .get()
                    .uri(uri);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    public <R> Mono<R> get(String serviceName, String uri, Class<R> responseType) {
        return get(serviceName, uri, responseType, null);
    }

    public <R> Mono<R> get(String serviceName, String uri, Class<R> responseType,
                           Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestHeadersSpec<?> request = getWebClient(serviceName)
                    .get()
                    .uri(uri);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    public <T, R> Mono<R> put(String serviceName, String uri, T requestBody,
                              Class<R> responseType) {
        return put(serviceName, uri, requestBody, responseType, null);
    }

    public <T, R> Mono<R> put(String serviceName, String uri, T requestBody,
                              Class<R> responseType, Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestBodySpec request = getWebClient(serviceName)
                    .put()
                    .uri(uri)
                    .contentType(MediaType.APPLICATION_JSON);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    public <R> Mono<R> delete(String serviceName, String uri, Class<R> responseType) {
        return delete(serviceName, uri, responseType, null);
    }

    public <R> Mono<R> delete(String serviceName, String uri, Class<R> responseType,
                              Consumer<HttpHeaders> headersConsumer) {
        return executeRequest(serviceName, () -> {
            WebClient.RequestHeadersSpec<?> request = getWebClient(serviceName)
                    .delete()
                    .uri(uri);

            if (headersConsumer != null) {
                request.headers(headersConsumer);
            }

            return request
                    .retrieve()
                    .bodyToMono(responseType);
        });
    }

    private <R> Mono<R> executeRequest(String serviceName, Supplier<Mono<R>> requestSupplier) {
        log.info("Executing request to {} service", serviceName);

        MicroserviceProperties.ServiceConfig config =
                microserviceProperties.getServiceConfig(serviceName);

        return requestSupplier.get()
                .retryWhen(Retry.backoff(config.getMaxRetries(),
                                Duration.ofMillis(config.getRetryBackoffMillis()))
                        .filter(errorHandler::isRetryableException)
                        .doBeforeRetry(retrySignal ->
                                log.warn("Retrying {} service request, attempt: {}",
                                        serviceName, retrySignal.totalRetries() + 1)))
                .doOnSuccess(response ->
                        log.info("{} service request completed successfully", serviceName))
                .doOnError(error ->
                        log.error("{} service request failed: {}", serviceName, error.getMessage()))
                .onErrorResume(error -> errorHandler.handleError(error, serviceName));
    }

    private WebClient getWebClient(String serviceName) {
        WebClient webClient = webClients.get(serviceName);
        if (webClient == null) {
            throw new IllegalArgumentException(
                    String.format("WebClient not configured for service: %s", serviceName));
        }
        return webClient;
    }
}
