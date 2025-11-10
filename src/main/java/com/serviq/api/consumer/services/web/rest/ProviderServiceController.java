package com.serviq.api.consumer.services.web.rest;

import com.serviq.api.consumer.services.dto.response.SlotResponse;
import com.serviq.api.consumer.services.service.ProviderServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
public class ProviderServiceController {

    private final ProviderServiceService providerServiceService;

    @GetMapping("/{id}/slots")
    public Mono<List<SlotResponse>> getAvailableSlots(@PathVariable UUID id,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate slotDate) {
        log.info("Request to get available slots for service: {}, date: {}", id, slotDate);
        return providerServiceService.findAvailableSlots(id, slotDate);
    }

}
