package com.ServiceAggregatingEvents.project.entities;

import lombok.Data;

import java.util.List;

@Data
public class ApiResponse {
    private List<Event> events;
}
