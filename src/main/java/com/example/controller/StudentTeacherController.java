package com.example.controller;

import com.example.dto.studentTeacher.CreateStudentTeacherDto;
import com.example.dto.studentTeacher.StudentTeacherDto;
import com.example.service.StudentTeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student_teacher")
@RequiredArgsConstructor
public class StudentTeacherController {

    private final StudentTeacherService studentTeacherService;

    @PostAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentTeacherDto> createStudentTeacher(@RequestBody CreateStudentTeacherDto createStudentTeacherDto) {
         return ResponseEntity.ok( studentTeacherService.create(createStudentTeacherDto));
    }
}
