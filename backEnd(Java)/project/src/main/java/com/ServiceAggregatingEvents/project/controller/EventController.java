package com.ServiceAggregatingEvents.project.controller;

import com.ServiceAggregatingEvents.project.dto.ApiResponseDto;
import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
import com.ServiceAggregatingEvents.project.dto.EditEventDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/my-events/{userId}")
    public ResponseEntity<ApiResponseDto<List<Event>>> getMyEvents(@PathVariable Long userId) {

     List<Event> returnEventsByUserId= eventService.getEventsByUserId(userId);
     return new ResponseEntity<>(new ApiResponseDto<>
             ("succes","All events by user id retrieved succesfully",returnEventsByUserId),HttpStatus.OK);

    }


    @GetMapping("/allEvents")
    public ResponseEntity<ApiResponseDto<List<CreateEventsDto>>> allEvents() {
        List<CreateEventsDto> resultAllEvents = eventService.findAllEvents();
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "All events retrieved successfully", resultAllEvents), HttpStatus.OK);
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ApiResponseDto<Optional<CreateEventsDto>>> getEventById(@PathVariable Long eventId) {
        Optional<CreateEventsDto> getEvent = eventService.findEventById(eventId);
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "Event retrieved successfully", getEvent), HttpStatus.OK);
    }
    @GetMapping("/my-created-events/{organizerId}")
    public ResponseEntity<ApiResponseDto<List<Event>>> getOrganizerEvent(@PathVariable Long organizerId){
        List<Event> returnAllOrganizerEventById = eventService.getEventsOrganizerById(organizerId);
        return new ResponseEntity<>(new ApiResponseDto<>
                ("succes","All organizer events retrieved successfully",returnAllOrganizerEventById)
                ,HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<ApiResponseDto<CreateEventsDto>> createEvent(@Valid @RequestBody CreateEventsDto createEventsDto) {
        System.out.println("Received request to create event");
        CreateEventsDto createdEvent = eventService.createEvent(createEventsDto);

        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "Event created successfully", createdEvent), HttpStatus.CREATED);
    }


    @PutMapping("/{eventId}")
    public ResponseEntity<ApiResponseDto<CreateEventsDto>> updateEvent(@PathVariable Long eventId, @Valid @RequestBody EditEventDto editEventDto) {
        CreateEventsDto updatedEvent = eventService.editEvent(eventId, editEventDto);
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "Event updated successfully", updatedEvent), HttpStatus.OK);
    }


    @DeleteMapping("/{eventId}")
    public ResponseEntity<ApiResponseDto<Void>> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(new ApiResponseDto<>(
                "success", "Event deleted successfully", null), HttpStatus.OK);
    }
}


