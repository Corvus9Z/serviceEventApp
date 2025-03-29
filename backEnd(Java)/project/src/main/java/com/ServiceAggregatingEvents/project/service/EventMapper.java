package com.ServiceAggregatingEvents.project.service;

import org.springframework.stereotype.Component;
import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.mapper.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EventMapper implements Mapper<Event, CreateEventsDto> {

    @Override
    public CreateEventsDto convertToDto(Event entity) {
        CreateEventsDto dto = new CreateEventsDto();
        dto.setId(entity.getId()); // ✅ Adăugat acum
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setOrganizerId(entity.getOrganizer().getId());
        dto.setLocation(entity.getLocation());
        dto.setOrganizerFullName(entity.getOrganizerFullName());
        return dto;
    }


    @Override
    public Event convertToEntity(CreateEventsDto dto) {
        Event event = new Event();
        event.setTitle(dto.getTitle());
        event.setDescription(dto.getDescription());
        event.setStartDate(dto.getStartDate());
        event.setEndDate(dto.getEndDate());
        event.setLocation(dto.getLocation());
        event.setOrganizerFullName(dto.getOrganizerFullName());



        return event;
    }
}
