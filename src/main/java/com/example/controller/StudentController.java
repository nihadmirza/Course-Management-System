package com.example.controller;


import com.example.dto.student.CreateStudentDto;
import com.example.dto.student.StudentDto;
import com.example.dto.student.UpdateStudentDto;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    private final StudentService studentService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<StudentDto> createStudent(@RequestBody CreateStudentDto studentDto) {
        return ResponseEntity.ok(studentService.createStudent(studentDto));
    }
    @GetMapping("/{id}")
    public ResponseEntity<StudentDto> getById(@PathVariable  Long id){
         return ResponseEntity.ok(studentService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<StudentDto>> getAll(){
        return ResponseEntity.ok(studentService.getAll());
    }

    @PostAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update/{id}")
    public ResponseEntity<StudentDto> update(@PathVariable("id") Long id, @RequestBody UpdateStudentDto studentDto){
        return ResponseEntity.ok(studentService.update(id, studentDto));

    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<StudentDto> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(studentService.delete(id));

    }



}
