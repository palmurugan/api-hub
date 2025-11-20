package com.serviq.api.consumer.booking.service.impl;

import com.serviq.api.consumer.booking.dto.request.ConfirmBookingRequest;
import com.serviq.api.consumer.booking.dto.request.CreateBookingRequest;
import com.serviq.api.consumer.booking.dto.response.BookingResponse;
import com.serviq.api.consumer.booking.service.BookingService;
import com.serviq.api.service.WebClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private static final String SERVICE_NAME = "booking-service";
    private static final String CREATE_BOOKING_URI = "/api/v1/bookings";
    private static final String CONFIRM_BOOKING_URI = "/api/v1/bookings/confirm";

    private final WebClientService webClientService;

    @Override
    public Mono<BookingResponse> createBooking(CreateBookingRequest request) {
        log.info("Received request to create booking: {}", request);
        return webClientService.post(SERVICE_NAME, CREATE_BOOKING_URI, request, BookingResponse.class);
    }

    @Override
    public Mono<BookingResponse> confirmBooking(ConfirmBookingRequest request) {
        log.info("Received request to confirm booking: {}", request);
        return webClientService.post(SERVICE_NAME, CONFIRM_BOOKING_URI, request, BookingResponse.class);
    }
}
