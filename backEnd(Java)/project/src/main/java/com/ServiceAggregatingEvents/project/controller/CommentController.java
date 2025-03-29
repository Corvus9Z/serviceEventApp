package com.ServiceAggregatingEvents.project.controller;

import com.ServiceAggregatingEvents.project.dto.CommentDto;
import com.ServiceAggregatingEvents.project.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> findAllComments(){
        List<CommentDto> allComments = commentService.findAllComments();
        return new ResponseEntity<>(allComments, HttpStatus.OK);
    }
    @GetMapping("/comments/{idComment}")
    public ResponseEntity<CommentDto> findByIdComment(@PathVariable Long idComment){
        CommentDto commentDto = commentService.findById(idComment).get();
        return new ResponseEntity<>(commentDto,HttpStatus.OK);
    }
    @PostMapping("/createComment")
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        CommentDto commentDto1 = commentService.createComment(commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
    @PutMapping("editComment/{idComment}")
    public ResponseEntity<CommentDto> editComment(@RequestBody CommentDto commentDto,@PathVariable Long idComment){
        CommentDto commentDto1 = commentService.editComment(idComment,commentDto);
        return new ResponseEntity<>(commentDto1,HttpStatus.OK);
    }
    @DeleteMapping("/deleteComment/{idComment}")
    public ResponseEntity<?> deleteComment(@PathVariable Long idComment){
        commentService.deleteComment(idComment);
        return new ResponseEntity<>("The comment with id :"+idComment+"has ben deleted successfully",
                HttpStatus.OK);
    }
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<CommentDto>> getCommentsByEventId(@PathVariable Long eventId) {
        List<CommentDto> comments = commentService.findCommentsByEventId(eventId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }


}
