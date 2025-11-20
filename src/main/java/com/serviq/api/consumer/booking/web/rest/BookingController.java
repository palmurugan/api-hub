package com.serviq.api.consumer.booking.web.rest;

import com.serviq.api.consumer.booking.dto.request.ConfirmBookingRequest;
import com.serviq.api.consumer.booking.dto.request.CreateBookingRequest;
import com.serviq.api.consumer.booking.dto.response.BookingResponse;
import com.serviq.api.consumer.booking.service.BookingService;
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
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping
    public Mono<BookingResponse> initiateBooking(@Valid @RequestBody CreateBookingRequest request) {
        log.info("Rest request to create booking: {}", request);
        return bookingService.createBooking(request);
    }

    @PostMapping("/confirm")
    public Mono<BookingResponse> confirmBooking(@Valid @RequestBody ConfirmBookingRequest request) {
        log.info("Rest request to confirm booking: {}", request);
        return bookingService.confirmBooking(request);
    }
}
