package com.roombook.room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RoomDTO(
        @NotBlank(message="Room name is required") String name,
        @Positive(message="Capacity must be positive") int capacity,
        @NotBlank(message="Amenities are required") String amenities) {}
