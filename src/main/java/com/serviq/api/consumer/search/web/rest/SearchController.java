package com.serviq.api.consumer.search.web.rest;

import com.serviq.api.consumer.search.service.SearchService;
import com.serviq.api.dto.request.SearchRequest;
import com.serviq.api.dto.response.SearchServiceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/consumer/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @PostMapping
    public Mono<List<SearchServiceResponse>> searchService(@Valid @RequestBody SearchRequest searchRequest) {
        log.info("Rest request to search service: {}", searchRequest.getKeyword());
        return searchService.searchServiceByKeyword(searchRequest);
    }
}
