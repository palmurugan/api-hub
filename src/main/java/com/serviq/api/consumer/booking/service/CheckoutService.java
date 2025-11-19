package com.serviq.api.consumer.booking.service;

import com.serviq.api.consumer.booking.dto.request.CheckoutInitiateRequest;
import com.serviq.api.consumer.booking.dto.response.CheckoutSessionResponse;
import reactor.core.publisher.Mono;

public interface CheckoutService {
    Mono<CheckoutSessionResponse> initiateCheckout(CheckoutInitiateRequest request);
}
