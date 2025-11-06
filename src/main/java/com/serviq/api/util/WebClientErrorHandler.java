package com.serviq.api.util;

import com.serviq.api.exception.BadRequestException;
import com.serviq.api.exception.ServiceException;
import com.serviq.api.exception.ServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class WebClientErrorHandler {

    public <T> Mono<T> handleError(Throwable error, String serviceName) {
        log.error("Error occurred while calling {} service: {}", serviceName, error.getMessage());

        if (error instanceof WebClientResponseException) {
            return handleWebClientResponseException((WebClientResponseException) error, serviceName);
        }

        if (error instanceof java.net.ConnectException) {
            return Mono.error(new ServiceUnavailableException(
                    String.format("Unable to connect to %s service", serviceName)));
        }

        if (error instanceof java.util.concurrent.TimeoutException ||
                error instanceof io.netty.handler.timeout.TimeoutException) {
            return Mono.error(new ServiceUnavailableException(
                    String.format("%s service request timeout", serviceName)));
        }

        return Mono.error(new ServiceException(
                String.format("Unexpected error from %s service: %s", serviceName, error.getMessage()),
                500));
    }

    private <T> Mono<T> handleWebClientResponseException(
            WebClientResponseException ex, String serviceName) {

        log.error("{} service error - Status: {}, Response: {}",
                serviceName, ex.getStatusCode(), ex.getResponseBodyAsString());

        return switch (ex.getStatusCode().value()) {
            case 400 -> Mono.error(new BadRequestException(
                    String.format("Invalid request to %s service: %s",
                            serviceName, ex.getResponseBodyAsString())));

            case 401 -> Mono.error(new ServiceException(
                    String.format("Unauthorized access to %s service", serviceName), 401));

            case 403 -> Mono.error(new ServiceException(
                    String.format("Forbidden access to %s service", serviceName), 403));

            case 404 -> Mono.error(new ServiceException(
                    String.format("Resource not found in %s service", serviceName), 404));

            case 409 -> Mono.error(new ServiceException(
                    String.format("Conflict in %s service: Resource already exists", serviceName), 409));

            case 422 -> Mono.error(new BadRequestException(
                    String.format("Unprocessable entity in %s service: %s",
                            serviceName, ex.getResponseBodyAsString())));

            case 429 -> Mono.error(new ServiceException(
                    String.format("Rate limit exceeded for %s service", serviceName), 429));

            case 503 -> Mono.error(new ServiceUnavailableException(
                    String.format("%s service is temporarily unavailable", serviceName)));

            default -> {
                if (ex.getStatusCode().is5xxServerError()) {
                    yield Mono.error(new ServiceUnavailableException(
                            String.format("%s service encountered an error", serviceName)));
                }
                yield Mono.error(new ServiceException(
                        String.format("Unexpected error from %s service", serviceName),
                        ex.getStatusCode().value()));
            }
        };
    }

    public boolean isRetryableException(Throwable throwable) {
        if (throwable instanceof WebClientResponseException ex) {
            return ex.getStatusCode().is5xxServerError() ||
                    ex.getStatusCode() == HttpStatus.REQUEST_TIMEOUT ||
                    ex.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS;
        }
        return throwable instanceof java.net.ConnectException ||
                throwable instanceof java.io.IOException ||
                throwable instanceof java.util.concurrent.TimeoutException ||
                throwable instanceof io.netty.handler.timeout.TimeoutException;
    }
}
