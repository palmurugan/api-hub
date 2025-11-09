package com.serviq.api.consumer.search.service.impl;

import com.serviq.api.consumer.search.service.SearchService;
import com.serviq.api.dto.request.SearchRequest;
import com.serviq.api.dto.response.SearchServiceResponse;
import com.serviq.api.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    private static final String SERVICE_NAME = "discovery-service";
    private static final String SEARCH_SERVICE_URI = "/api/v1/search";

    private final WebClientService webClientService;

    @Override
    public Mono<List<SearchServiceResponse>> searchServiceByKeyword(SearchRequest request) {
        log.info("Received request to search service: {}", request.getKeyword());
        return webClientService.post(SERVICE_NAME, SEARCH_SERVICE_URI, request,
                new ParameterizedTypeReference<>() {
                });
    }
}
