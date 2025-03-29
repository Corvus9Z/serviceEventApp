package com.ServiceAggregatingEvents.project.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CreateEventsDto {


    private Long id; // ✅ Asta lipsea!
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100, message = "Title can have max 100 characters")
    private String title;

    @NotBlank(message = "Description is mandatory")
    @Size(min = 20, message = "Description must have at least 20 characters")
    private String description;

    @NotNull(message = "Start date is mandatory")
    @FutureOrPresent(message = "Start date must be in the present or future")
    private LocalDateTime startDate;

    @NotNull(message = "End date is mandatory")
    @FutureOrPresent(message = "End date must be in the present or future")
    private LocalDateTime endDate;

    @NotNull(message = "Organizer is mandatory")
    private Long organizerId;
    @NotBlank(message = "Location is mandatory")
    private String location;
    @NotBlank(message = "Organizer name is required")
    private String organizerFullName;


}
