//package com.ServiceAggregatingEvents.project.service;
//
//import com.ServiceAggregatingEvents.project.dto.CreateEventsDto;
//import com.ServiceAggregatingEvents.project.entities.Event;
//import com.ServiceAggregatingEvents.project.exceptions.ResourceNotFoundException;
//import com.ServiceAggregatingEvents.project.repository.EventRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class EventCreateService {
//    @Autowired
//    private EventRepo eventRepo;
//    @Autowired
//    private EventCreateMapper eventCreateMapper;
//
//
//    public List<CreateEventsDto> findAllEvents(){
//        return eventRepo.findAll().stream().map(event -> eventCreateMapper.convertToDto(event))
//                .collect(Collectors.toList());
//            }
//            public Optional<CreateEventsDto> findByName(String name){
//                Event event = eventRepo
//                        .findByTitle(name)
//                        .orElseThrow(()->new ResourceNotFoundException("Event with the name:"+name+"was not found"));
//                return Optional.ofNullable(eventCreateMapper.convertToDto(event));
//            }
//            public CreateEventsDto createEvents(CreateEventsDto createEventsDto){
//                 Event event = eventCreateMapper.convertToEntity(createEventsDto);
//                 eventRepo.save(event);
//                 return eventCreateMapper.convertToDto(event);
//            }
//            public CreateEventsDto editEvents(String name,CreateEventsDto createEventsDto){
//             Event event = eventRepo.findByTitle(name)
//                     .orElseThrow(()->new ResourceNotFoundException("Event with the name:"+name+"was not found"));
//                event.setTitle(createEventsDto.getTitle());
//                event.setDescription(createEventsDto.getDescription());
//                event.setStartDate(createEventsDto.getStartDate());
//                event.setEndDate(createEventsDto.getEndDate());
//                eventRepo.save(event);
//                return eventCreateMapper.convertToDto(event);
//            }
//    public void deleteEvent(String name) {
//        Event event = eventRepo.findByTitle(name)
//                .orElseThrow(() -> new ResourceNotFoundException("Event with the name " + name + " not found"));
//        eventRepo.delete(event);
//    }
//}
