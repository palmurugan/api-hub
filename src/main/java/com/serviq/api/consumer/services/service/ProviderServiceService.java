package com.serviq.api.consumer.services.service;

import com.serviq.api.consumer.services.dto.response.SlotResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ProviderServiceService {

    Mono<List<SlotResponse>> findAvailableSlots(UUID providerServiceId, LocalDate slotDate);
}
