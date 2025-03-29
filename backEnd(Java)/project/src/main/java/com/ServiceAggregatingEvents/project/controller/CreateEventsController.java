//package com.ServiceAggregatingEvents.project.controller;
//
//import com.ServiceAggregatingEvents.project.dto.ApiResponseDto;
//import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
//import com.ServiceAggregatingEvents.project.service.EventCreateService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/createEvents")
//@RequiredArgsConstructor
//public class CreateEventsController {
//    private final EventCreateService eventCreateService;
//
//    @PreAuthorize("hasRole('ADMIN') or hasRole('ORGANIZER')")
//    @PostMapping("/createEvents")
//    public ResponseEntity<ApiResponseDto<CreateEventsDto>> createEvents(@RequestBody CreateEventsDto createEventsDto){
//        System.out.println("Attempting to create event by user with roles: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//
//        CreateEventsDto createEvent = eventCreateService.createEvents(createEventsDto);
//        return new ResponseEntity<>(new ApiResponseDto<>
//                ("success","Event was created successfully",createEvent), HttpStatus.OK);
//    }
//
//}
