package com.ServiceAggregatingEvents.project.dto;

import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.entities.User;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventSignUpDto {



    @NotNull(message = "Event ID is mandatory")
    private Long eventId;

    @NotNull(message = "User ID is mandatory")
    private Long userId;

    private LocalDateTime signupTime = LocalDateTime.now();
    private User user;
    private Event event;

    // No need for Event, User, or signupTime fields in the DTO
}

