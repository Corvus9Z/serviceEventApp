package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.EventSignUpDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.entities.EventSignUp;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.mapper.Mapper;
import com.ServiceAggregatingEvents.project.repository.EventRepo;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventSignUpMapper implements Mapper<EventSignUp, EventSignUpDto> {

    private final UserRepo userRepo;
    private final EventRepo eventRepo;

    public EventSignUpMapper(UserRepo userRepo,
                             EventRepo eventRepo) {
        this.userRepo = userRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public EventSignUpDto convertToDto(EventSignUp entity) {
        EventSignUpDto dto = new EventSignUpDto();
        dto.setUserId(entity.getUser().getId());
        dto.setEventId(entity.getEvent().getId());
        dto.setSignupTime(entity.getSignupTime());
        return dto;
    }

    @Override
    public EventSignUp convertToEntity(EventSignUpDto dto) {
        EventSignUp eventSignUp = new EventSignUp();
        eventSignUp.setSignupTime(dto.getSignupTime());

        // ✅ Obține utilizatorul din baza de date
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + dto.getUserId()));
        eventSignUp.setUser(user);

        // ✅ Obține evenimentul din baza de date
        Event event = eventRepo.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + dto.getEventId()));
        eventSignUp.setEvent(event);

        return eventSignUp;
    }

    public List<EventSignUpDto> listConvertToDto(List<EventSignUp> entities) {
        List<EventSignUpDto> result = new ArrayList<>();
        for (EventSignUp eventSignUp : entities) {
            result.add(convertToDto(eventSignUp));
        }
        return result;
    }

    public List<EventSignUp> listConvertToEntity(List<EventSignUpDto> dtos) {
        List<EventSignUp> results = new ArrayList<>();
        for (EventSignUpDto dto : dtos) {
            results.add(convertToEntity(dto));
        }
        return results;
    }
}
