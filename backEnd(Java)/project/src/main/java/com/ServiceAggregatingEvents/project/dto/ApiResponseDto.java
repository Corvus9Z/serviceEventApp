package com.ServiceAggregatingEvents.project.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {

    private String status;
    private String message;
    private T data;
}

