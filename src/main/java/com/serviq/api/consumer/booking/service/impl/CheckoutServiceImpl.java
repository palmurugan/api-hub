package com.serviq.api.consumer.booking.service.impl;

import com.serviq.api.consumer.booking.dto.request.CheckoutInitiateRequest;
import com.serviq.api.consumer.booking.dto.response.CheckoutSessionResponse;
import com.serviq.api.consumer.booking.service.CheckoutService;
import com.serviq.api.exception.BadRequestException;
import com.serviq.api.service.WebClientService;
import com.serviq.api.util.ApplicationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private static final String SERVICE_NAME = "checkout-service";
    private static final String INITIATE_CHECKOUT_URI = "/api/v1/checkout/initiate";

    private final WebClientService webClientService;

    @Override
    public Mono<CheckoutSessionResponse> initiateCheckout(CheckoutInitiateRequest request) {
        log.info("Request to initiate checkout: {}", request);
        return Mono.deferContextual(ctx -> {
            UUID orgId = (UUID) ctx.getOrEmpty(ApplicationConstants.CONTEXT_ORG_ID).orElse(null);
            UUID userId = (UUID) ctx.getOrEmpty(ApplicationConstants.CONTEXT_USER_ID).orElse(null);
            log.debug("Extracted orgId: {}, userId: {}", orgId, userId);
            if (orgId == null || userId == null) {
                return Mono.error(new BadRequestException("Organization ID / User ID is required"));
            }
            request.setOrgId(orgId);
            request.setUserId(userId);
            return webClientService.post(SERVICE_NAME, INITIATE_CHECKOUT_URI, request, CheckoutSessionResponse.class);
        });
    }
}
