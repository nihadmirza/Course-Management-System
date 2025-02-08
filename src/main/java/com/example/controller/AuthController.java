package com.example.controller;

import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RefreshTokenRequestDto;
import com.example.service.StudentService;
import com.example.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final StudentService studentService;
    private final TeacherService teacherService;

    @PostMapping("/teacher/login")
    public ResponseEntity<LoginResponse> loginTeacher(@RequestBody LoginRequest loginRequest){
        return  ResponseEntity.ok(teacherService.login(loginRequest));
    }


    @PostMapping("teacher/refresh")
    public  ResponseEntity<LoginResponse> refreshTeacher(@RequestBody RefreshTokenRequestDto request){
        return  ResponseEntity.ok(teacherService.refresh(request));
    }

    @PostMapping("/student/login")
    public ResponseEntity<LoginResponse> studentLogin(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(studentService.login(loginRequest));

    }
    @PostMapping("student/refresh")
    public  ResponseEntity<LoginResponse> refreshStudent(@RequestBody RefreshTokenRequestDto request){
        return  ResponseEntity.ok(studentService.refresh(request));
    }


 }
