package com.ServiceAggregatingEvents.project.repository;

import com.ServiceAggregatingEvents.project.entities.EventSignUp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventSingUpRepo extends JpaRepository<EventSignUp,Long> {
    Optional<EventSignUp> findByEventId(Long eventID);
    Optional<EventSignUp> findByEvent_Title(String title);
    List<EventSignUp> findByUser_Id(Long userId);
    Optional<EventSignUp> findByUser_IdAndEvent_Id(Long userId, Long eventId);

}
