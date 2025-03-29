package com.ServiceAggregatingEvents.project.repository;

import com.ServiceAggregatingEvents.project.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {
    Optional<Object> findByEventId(Long eventId);
    List<Comment> findByEvent_Id(Long eventId);

    Optional<Object> findByEventTitle(String title);

}
