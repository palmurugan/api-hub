package com.serviq.api.service.impl;

import com.serviq.api.dto.request.CreateProviderRequest;
import com.serviq.api.dto.response.PageResponse;
import com.serviq.api.dto.response.ProviderResponse;
import com.serviq.api.exception.BadRequestException;
import com.serviq.api.service.ProviderService;
import com.serviq.api.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {

    private static final String SERVICE_NAME = "provider-service";
    private static final String CREATE_PROVIDER_URI = "/api/v1/providers";

    private final WebClientService webClientService;

    @Override
    public Mono<ProviderResponse> createProvider(CreateProviderRequest request) {
        log.info("Creating provider with name: {}", request.getName());
        return Mono.deferContextual(ctx -> {
            String orgId = (String) ctx.getOrEmpty("orgId").orElse(null);
            if (orgId == null) {
                return Mono.error(new BadRequestException("Organization ID is required"));
            }
            request.setOrgId(orgId);
            return webClientService.post(SERVICE_NAME, CREATE_PROVIDER_URI, request, ProviderResponse.class);
        });
    }

    @Override
    public Mono<PageResponse<ProviderResponse>> getProviders(int page, int size) {
        return webClientService.get(SERVICE_NAME, "/api/v1/providers",
                new ParameterizedTypeReference<>() {
                });
    }
}
