package com.example.exception.handler;


import com.example.exception.CustomAccessDeniedException;
import com.example.exception.LessonNotFoundException;
import com.example.exception.StudentNotFoundException;
import com.example.exception.TeacherNotFoundException;
import com.example.exception.model.ErrorResponce;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponce handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return  ErrorResponce.builder()
                .errors(errors)
                .message(ex.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(LessonNotFoundException.class)
    public ErrorResponce handleLessonNotFoundException(LessonNotFoundException exception,
                                                     HttpServletRequest request) {
     return  ErrorResponce.builder()
                     .message(exception.getMessage())
                     .timestamp(LocalDateTime.now())
                     .path(request.getServletPath())
                     .build();

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(StudentNotFoundException.class)
    public ErrorResponce handleStudentNotFoundException(StudentNotFoundException exception,
                                                       HttpServletRequest request) {
        return  ErrorResponce.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(TeacherNotFoundException.class)
    public ErrorResponce handleTeacherNotFoundException(TeacherNotFoundException exception,
                                                       HttpServletRequest request) {
        return  ErrorResponce.builder()
                .message(exception.getMessage())
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponce handleException(Exception exception,
                       HttpServletRequest request) {
        return  ErrorResponce.builder()
                .message("Something went wrong")
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();

    }



@ExceptionHandler(CustomAccessDeniedException.class)
    public ErrorResponce handleAccessDeniedException(CustomAccessDeniedException ex, HttpServletRequest request) {
        return ErrorResponce.builder()
                .message(ex.getMessage())  // Custom message from the exception
                .timestamp(LocalDateTime.now())
                .path(request.getServletPath())
                .build();
    }







}
