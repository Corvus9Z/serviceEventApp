package com.ServiceAggregatingEvents.project.repository;

import com.ServiceAggregatingEvents.project.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepo extends JpaRepository<Event,Long> {

    Optional<Event> findByTitle(String title);
    List<Event> findByEndDateAfter(LocalDateTime currentDate);
    List<Event> findByStartDateBeforeAndEndDateAfter(LocalDateTime startDate,LocalDateTime endDate);
    List<Event> findByOrganizer_Id(Long organizerId);

}
