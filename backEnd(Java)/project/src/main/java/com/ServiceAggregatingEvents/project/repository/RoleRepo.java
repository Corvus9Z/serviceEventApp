package com.ServiceAggregatingEvents.project.repository;

import com.ServiceAggregatingEvents.project.entities.Role;
import com.ServiceAggregatingEvents.project.entities.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepo extends JpaRepository<Role,Long> {
    Optional<Role> findByName (RoleType name);

}
