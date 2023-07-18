package com.ibk.customers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ErrorDetails {
    private LocalDateTime timestamp;

    private Integer statusCode;

    private String error;

    private String message;
}
