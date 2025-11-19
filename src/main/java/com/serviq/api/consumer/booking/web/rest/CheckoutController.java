package com.serviq.api.consumer.booking.web.rest;

import com.serviq.api.consumer.booking.dto.request.CheckoutInitiateRequest;
import com.serviq.api.consumer.booking.dto.response.CheckoutSessionResponse;
import com.serviq.api.consumer.booking.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("/api/v1/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping("/initiate")
    public Mono<CheckoutSessionResponse> initiateCheckout(@Valid @RequestBody CheckoutInitiateRequest request) {
        log.info("POST /api/v1/checkout/initiate - User: {}, Service: {}",
                request.getUserId(), request.getServiceId());
        return checkoutService.initiateCheckout(request);
    }
}
