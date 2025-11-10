package com.serviq.api.consumer.services.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
public class SlotResponse {

    private UUID id;
    private UUID orgId;
    private UUID providerId;
    private UUID providerServiceId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate slotDate;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm:ss")
    private LocalTime endTime;
    private Integer durationMinutes;
    private Integer capacity;
    private Integer bookedCount;
    private SlotStatus status;
    private Boolean isAvailable;

    enum SlotStatus {
        AVAILABLE,
        BOOKED,
        BLOCKED,
        CANCELLED
    }
}
