package com.serviq.api.web.rest.admin;

import com.serviq.api.dto.request.CreateProviderRequest;
import com.serviq.api.dto.response.ProviderResponse;
import com.serviq.api.service.ProviderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/providers")
@RequiredArgsConstructor
public class ProviderController {

    private final ProviderService providerService;

    @PostMapping
    public Mono<ResponseEntity<ProviderResponse>> createProvider(
            @Valid @RequestBody CreateProviderRequest request) {
        log.info("Received create to create provider {}", request.getName());

        return providerService.createProvider(request)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED)
                        .body(response))
                .doOnSuccess(response ->
                        log.info("Provider creation request completed successfully"));
    }
}
