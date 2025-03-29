package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.CommentDto;
import com.ServiceAggregatingEvents.project.entities.Comment;
import com.ServiceAggregatingEvents.project.entities.Event;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.mapper.Mapper;
import com.ServiceAggregatingEvents.project.repository.EventRepo;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentMapper implements Mapper<Comment, CommentDto> {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    @Override
    public CommentDto convertToDto(Comment comment) {
        CommentDto dto = new CommentDto();
        dto.setUserId(comment.getUser().getId());
        dto.setEventId(comment.getEvent().getId());
        dto.setContent(comment.getContent());
        dto.setCreatedAt(comment.getTimestamp());
        dto.setUserFullName(comment.getUser().getName() + " " + comment.getUser().getLastName());
        return dto;
    }

    @Override
    public Comment convertToEntity(CommentDto dto) {
        Comment comment = new Comment();
        comment.setContent(dto.getContent());

        // 🧠 Setăm User și Event doar pe baza ID-urilor
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getUserId()));
        Event event = eventRepo.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + dto.getEventId()));

        comment.setUser(user);
        comment.setEvent(event);

        return comment;
    }

    public List<CommentDto> listConvertToDto(List<Comment> entities) {
        List<CommentDto> result = new ArrayList<>();
        for (Comment comment : entities) {
            result.add(convertToDto(comment));
        }
        return result;
    }

    public List<Comment> listConvertToEntity(List<CommentDto> dtos) {
        List<Comment> result = new ArrayList<>();
        for (CommentDto dto : dtos) {
            result.add(convertToEntity(dto));
        }
        return result;
    }
}
