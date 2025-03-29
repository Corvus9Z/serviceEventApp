package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.EventSignUpDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.entities.EventSignUp;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.exceptions.ResourceNotFoundException;
import com.ServiceAggregatingEvents.project.repository.EventRepo;
import com.ServiceAggregatingEvents.project.repository.EventSingUpRepo;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventSignUpService {

    @Autowired
    private EventSingUpRepo eventSingUpRepo;
    @Autowired
    private EventSignUpMapper eventSignUpMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private EventRepo eventRepo;

    public List<EventSignUpDto> findAllEventsSignUp(){
        return eventSingUpRepo.findAll().stream().map(eventSignUp -> eventSignUpMapper.convertToDto(eventSignUp))
                .collect(Collectors.toList());
    }
    public Optional<EventSignUpDto> findById(Long id){
        EventSignUp eventSignUp = eventSingUpRepo.findById
                (id).orElseThrow(()->new ResourceNotFoundException("Event sign up with the id:"+id+"not found"));
        return Optional.ofNullable(eventSignUpMapper.convertToDto(eventSignUp));
    }
    public List<EventSignUpDto> findSignUpUserById(Long userId){
        return eventSingUpRepo
                .findByUser_Id(userId).stream().map(eventSignUp -> eventSignUpMapper.convertToDto(eventSignUp))
                .collect(Collectors.toList());



    }
    public EventSignUpDto createEventSignUpDto(EventSignUpDto eventSignUpDto) {
        // ✅ Verificare corectă dacă userul este deja înscris la ACEL eveniment
        Optional<EventSignUp> alreadyJoined = eventSingUpRepo.findByUser_IdAndEvent_Id(
                eventSignUpDto.getUserId(), eventSignUpDto.getEventId()
        );

        if (alreadyJoined.isPresent()) {
            throw new RuntimeException("User is already signed up for this event");
        }

        EventSignUp eventSignUp = eventSignUpMapper.convertToEntity(eventSignUpDto);

        if (eventSignUpDto.getUserId() == null || eventSignUpDto.getEventId() == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Missing userId or eventId");
        }

        User user = userRepo.findById(eventSignUpDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + eventSignUpDto.getUserId()));

        Event event = eventRepo.findById(eventSignUpDto.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found with ID: " + eventSignUpDto.getEventId()));

        eventSignUp.setUser(user);
        eventSignUp.setEvent(event);

        eventSingUpRepo.save(eventSignUp);
        return eventSignUpMapper.convertToDto(eventSignUp);
    }

    public EventSignUpDto editEventSignUpDto(Long id,EventSignUpDto eventSignUpDto){
        EventSignUp eventSignUp = eventSingUpRepo.findById
                (id).orElseThrow(()->new ResourceNotFoundException("Event sign up with the id:"+id+"not found"));
        eventSignUp.setEvent(eventSignUpDto.getEvent());
        eventSignUp.setUser(eventSignUpDto.getUser());
        eventSingUpRepo.save(eventSignUp);
        return eventSignUpMapper.convertToDto(eventSignUp);
    }
    public void deleteEventSignUp(Long eventId) {
        EventSignUp eventSignUp = eventSingUpRepo.findByEventId(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("EventSignUp not found"));
        eventSingUpRepo.delete(eventSignUp);
    }
    public Optional<EventSignUpDto> findEventSignUpByName(String title){
        EventSignUp eventSignUp = (EventSignUp) eventSingUpRepo.findByEvent_Title(title)
                .orElseThrow(()->new ResourceNotFoundException("EventSignUp with the name"+title+"was not found"));

        return Optional.ofNullable(eventSignUpMapper.convertToDto(eventSignUp));
    }
    public List<Event> getEventsByUserId(Long userId){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        List<EventSignUp> signUps = eventSingUpRepo.findByUser_Id(userId);
        return signUps.stream().map(EventSignUp::getEvent).toList();
    }

}
