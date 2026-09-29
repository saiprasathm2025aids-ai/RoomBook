package com.roombook.booking;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record BookingDTO(
        @NotNull(message="Room is required") Long roomId,
        @NotNull(message="Organizer is required") Long organizerId,
        @NotNull(message="Start time is required") LocalDateTime startTime,
        @NotNull(message="End time is required") LocalDateTime endTime) {}
