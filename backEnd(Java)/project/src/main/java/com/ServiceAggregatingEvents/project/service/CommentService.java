package com.ServiceAggregatingEvents.project.service;

import com.ServiceAggregatingEvents.project.dto.CommentDto;
import com.ServiceAggregatingEvents.project.entities.Comment;
import com.ServiceAggregatingEvents.project.entities.User;
import com.ServiceAggregatingEvents.project.exceptions.ResourceNotFoundException;
import com.ServiceAggregatingEvents.project.repository.CommentRepo;
import com.ServiceAggregatingEvents.project.repository.EventRepo;
import com.ServiceAggregatingEvents.project.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EventRepo eventRepo;

    // ✅ Returnează toate comentariile din baza de date (convertite în DTO)
    public List<CommentDto> findAllComments() {
        return commentRepo.findAll().stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    // ✅ Returnează un comentariu după ID
    public Optional<CommentDto> findById(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with the id: " + id + " not found"));
        return Optional.of(commentMapper.convertToDto(comment));
    }

    // ✅ Creează un nou comentariu și setează timestamp
    public CommentDto createComment(CommentDto commentDto) {
        Comment comment = commentMapper.convertToEntity(commentDto);
        comment.setTimestamp(LocalDateTime.now());

        // Salvăm comentariul
        comment = commentRepo.save(comment);

        // Asigurăm user-ul complet populat pentru afișarea corectă în frontend
        User user = userRepo.findById(comment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found after saving"));

        comment.setUser(user); // resetăm pentru nume complet în DTO
        System.out.println("💬 Comment after save: " + comment);
        System.out.println("👤 User: " + user.getName() + " " + user.getLastName());

        return commentMapper.convertToDto(comment);
    }

    // ✅ Editează un comentariu existent
    public CommentDto editComment(Long id, CommentDto commentDto) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: " + id + " not found"));
        comment.setContent(commentDto.getContent());
        commentRepo.save(comment);
        return commentMapper.convertToDto(comment);
    }

    // ✅ Șterge un comentariu
    public void deleteComment(Long id) {
        Comment comment = commentRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with id: " + id + " not found"));
        commentRepo.delete(comment);
    }

    // ✅ Returnează toate comentariile de la un eveniment
    public List<CommentDto> findCommentsByEventId(Long eventId) {
        return commentRepo.findByEvent_Id(eventId).stream()
                .map(commentMapper::convertToDto)
                .collect(Collectors.toList());
    }
}
