package com.ServiceAggregatingEvents.project.controller;

import com.ServiceAggregatingEvents.project.dto.ApiResponseDto;
import com.ServiceAggregatingEvents.project.dto.EventSignUpDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.service.EventSignUpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/signUp")
@RequiredArgsConstructor
public class EventSignUpController {
    private final EventSignUpService eventSignUpService;
    private Event event;

    @GetMapping("/eventsSignUp")
    public ResponseEntity<ApiResponseDto<List<EventSignUpDto>>> findAllSignUpEvents() {
        List<EventSignUpDto> allSignUpEvents = eventSignUpService.findAllEventsSignUp();
        return new ResponseEntity<>(new ApiResponseDto<>("success", "All event sign-ups retrieved successfully", allSignUpEvents), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponseDto<List<EventSignUpDto>>> getSignUpForUser(@PathVariable Long userId){
        List<EventSignUpDto> signUps = eventSignUpService.findSignUpUserById(userId);
        return new ResponseEntity<>(new ApiResponseDto<>
                ("success","User sign-ups retrieved successfully",signUps),HttpStatus.OK);
    }

  @GetMapping("/event/{eventId}")
    public ResponseEntity<ApiResponseDto<Optional<EventSignUpDto>>> getSignUpEvents(@PathVariable Long eventId){
        Optional<EventSignUpDto> signUps = eventSignUpService.findById(eventId);
        return new ResponseEntity<>(new ApiResponseDto<>
                ("success","Event sign-ups retrieved successfully",signUps),HttpStatus.OK);
  }

  @PostMapping
    public ResponseEntity<ApiResponseDto<EventSignUpDto>> signUpForEvent(@RequestBody EventSignUpDto eventSignUpDto){
         EventSignUpDto createSignUp = eventSignUpService.createEventSignUpDto(eventSignUpDto);
      return new ResponseEntity<>(new ApiResponseDto<>
              ("success", "User signed up for event successfully", createSignUp), HttpStatus.CREATED);
  }
  @PreAuthorize("hasRole('ADMIN')")
 @PutMapping("/{signUpId}")
public ResponseEntity<ApiResponseDto<EventSignUpDto>> editSignUp(@RequestBody EventSignUpDto eventSignUpDto,@PathVariable Long signUpId){
        EventSignUpDto updateSignUp = eventSignUpService.editEventSignUpDto(signUpId,eventSignUpDto);
        return new ResponseEntity<>(new ApiResponseDto<>
                ("success","Your sign up event updated successfully",updateSignUp),HttpStatus.OK);
 }
 @PreAuthorize("hasRole('ADMIN')")
 @DeleteMapping("/{signUpId}")
 public ResponseEntity<ApiResponseDto<EventSignUpDto>> deleteSignUp(@PathVariable Long signUpId) {
     eventSignUpService.deleteEventSignUp(signUpId);
     return new ResponseEntity<>(new ApiResponseDto<>
             ("success", "Sign-up canceled successfully", null), HttpStatus.OK);
 }

}
