//package com.ServiceAggregatingEvents.project.service;
//import org.springframework.stereotype.Component;
//import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
//import com.ServiceAggregatingEvents.project.entities.Event;
//import com.ServiceAggregatingEvents.project.mapper.Mapper;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class EventCreateMapper implements Mapper<Event, CreateEventsDto> {
//    @Override
//    public CreateEventsDto convertToDto(Event entity) {
//        CreateEventsDto createEventsDto = new CreateEventsDto();
//        createEventsDto.setTitle(entity.getTitle());
//        createEventsDto.setDescription(entity.getDescription());
//        createEventsDto.setStartDate(entity.getStartDate());
//        createEventsDto.setEndDate(entity.getEndDate());
//        return createEventsDto;
//    }
//
//    @Override
//    public Event convertToEntity(CreateEventsDto dto) {
//        Event event = new Event();
//        event.setTitle(dto.getTitle());
//        event.setDescription(dto.getDescription());
//        event.setStartDate(dto.getStartDate());
//        event.setEndDate(dto.getEndDate());
//        return event;
//    }
//    public List<CreateEventsDto> listConvertToDto(List<Event> entity){
//        List<CreateEventsDto> result = new ArrayList<>();
//        for (Event event:entity){
//            CreateEventsDto convertToDto = convertToDto(event);
//            result.add(convertToDto);
//        }
//        return result;
//
//    }
//    public List<Event> listConvertToEntity(List<CreateEventsDto> dtos){
//        List<Event> result = new ArrayList<>();
//        for (CreateEventsDto createEventsDto:dtos){
//            Event convertToEntity = convertToEntity(createEventsDto);
//            result.add(convertToEntity);
//        }
//        return result;
//    }
//}
