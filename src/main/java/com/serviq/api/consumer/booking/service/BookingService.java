package com.serviq.api.consumer.booking.service;

import com.serviq.api.consumer.booking.dto.request.ConfirmBookingRequest;
import com.serviq.api.consumer.booking.dto.request.CreateBookingRequest;
import com.serviq.api.consumer.booking.dto.response.BookingResponse;
import reactor.core.publisher.Mono;

public interface BookingService {
    Mono<BookingResponse> createBooking(CreateBookingRequest request);

    Mono<BookingResponse> confirmBooking(ConfirmBookingRequest request);
}
