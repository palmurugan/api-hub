package com.serviq.api.service;

import com.serviq.api.dto.request.CreateProviderRequest;
import com.serviq.api.dto.response.PageResponse;
import com.serviq.api.dto.response.ProviderResponse;
import reactor.core.publisher.Mono;

public interface ProviderService {
    Mono<ProviderResponse> createProvider(CreateProviderRequest request);
    Mono<PageResponse<ProviderResponse>> getProviders(int page, int size);
}
