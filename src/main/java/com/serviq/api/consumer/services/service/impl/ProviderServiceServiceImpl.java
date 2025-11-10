package com.serviq.api.consumer.services.service.impl;

import com.serviq.api.consumer.services.dto.response.SlotResponse;
import com.serviq.api.consumer.services.service.ProviderServiceService;
import com.serviq.api.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProviderServiceServiceImpl implements ProviderServiceService {

    private static final String PROVIDER_SERVICE = "provider-service";
    private static final String FIND_AVAILABLE_SLOTS_URI = "/api/v1/slots/available?providerServiceId=%s&slotDate=%s";

    private final WebClientService webClientService;

    @Override
    public Mono<List<SlotResponse>> findAvailableSlots(UUID providerServiceId, LocalDate slotDate) {
        log.info("Received request to find available slots for provider service: {}", providerServiceId);
        return webClientService.get(PROVIDER_SERVICE, String.format(FIND_AVAILABLE_SLOTS_URI, providerServiceId, slotDate), new ParameterizedTypeReference<>() {
        });
    }
}
