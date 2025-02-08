package com.example.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponce {
    private String message;
    private LocalDateTime timestamp;
    private String path;
    private Map<String, String> errors;


}
