package com.serviq.api.consumer.search.service;

import com.serviq.api.dto.request.SearchRequest;
import com.serviq.api.dto.response.SearchServiceResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface SearchService {
    Mono<List<SearchServiceResponse>> searchServiceByKeyword(SearchRequest request);
}
