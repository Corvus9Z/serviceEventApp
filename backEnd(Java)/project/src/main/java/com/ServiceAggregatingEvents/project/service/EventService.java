package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.entities.EventSignUp;
import com.ServiceAggregatingEvents.project.repository.EventSingUpRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
import com.ServiceAggregatingEvents.project.dto.EditEventDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.exceptions.ResourceNotFoundException;
import com.ServiceAggregatingEvents.project.repository.EventRepo;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventMapper eventMapper;
    @Autowired
    private EventSingUpRepo eventSingUpRepo;

    public List<CreateEventsDto> findAllEvents() {
        return eventRepo.findAll().stream()
                .map(eventMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public Optional<CreateEventsDto> findEventById(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found"));
        return Optional.ofNullable(eventMapper.convertToDto(event));
    }
    @Transactional
    public CreateEventsDto createEvent(CreateEventsDto createEventsDto) {

        Event event = eventMapper.convertToEntity(createEventsDto);

        // Verificăm dacă DTO-ul vine deja cu ID, ceea ce n-ar trebui

        User organizer = userRepo.findById(createEventsDto.getOrganizerId())
                .orElseThrow(() -> new RuntimeException("Organizer not found"));

        event.setOrganizer(organizer);

        // Ar trebui să fie NULL aici, Hibernate trebuie să-l genereze

        event = eventRepo.save(event);


        return eventMapper.convertToDto(event);
    }




    public CreateEventsDto editEvent(Long eventId, EditEventDto editEventDto) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found"));
        event.setTitle(editEventDto.getTitle());
        event.setDescription(editEventDto.getDescription());
        event.setStartDate(editEventDto.getStartDate());
        event.setEndDate(editEventDto.getEndDate());
        eventRepo.save(event);
        return eventMapper.convertToDto(event);
    }

    public void deleteEvent(Long eventId) {
        Event event = eventRepo.findById(eventId)
                .orElseThrow(() -> new ResourceNotFoundException("Event with ID " + eventId + " not found"));
        eventRepo.delete(event);
    }

    public Optional<CreateEventsDto> findEventByTitle(String title) {
        Event event = eventRepo.findByTitle(title)
                .orElseThrow(() -> new ResourceNotFoundException("Event with title " + title + " not found"));
        return Optional.ofNullable(eventMapper.convertToDto(event));
    }
    public List<Event> getEventsByUserId(Long userId){
        User user = userRepo.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        List<EventSignUp> signUps = eventSingUpRepo.findByUser_Id(userId);
        return signUps.stream().map(EventSignUp::getEvent).toList();
    }
    public List<Event> getEventsOrganizerById(Long organizerId){
        User user = userRepo.findById(organizerId)
                .orElseThrow(() -> new RuntimeException("User not Found"));

        List<Event> createdEventsByOrganizer = eventRepo.findByOrganizer_Id(organizerId);


        return createdEventsByOrganizer;
    }


}
