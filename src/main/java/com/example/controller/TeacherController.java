package com.example.controller;

import com.example.dto.teacher.CreateTeacherDto;
import com.example.dto.teacher.TeacherDto;
import com.example.dto.teacher.UpdateTeacherDto;
import com.example.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("teacher")
@RequiredArgsConstructor
@Slf4j
public class TeacherController {
    private final TeacherService teacherService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TeacherDto> createTeacher(@RequestBody CreateTeacherDto teacherDto) {
        return ResponseEntity.ok(teacherService.createTeacher(teacherDto));
    }


    @GetMapping("/{id}")
    public ResponseEntity<TeacherDto> getById(@PathVariable  Long id){
         return ResponseEntity.ok(teacherService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<TeacherDto>> getAll(){
        return ResponseEntity.ok(teacherService.getAll());
    }


    @PatchMapping("/update/{id}")
    public ResponseEntity<TeacherDto> update(@PathVariable("id") Long id, @RequestBody UpdateTeacherDto teacherDto){
        return ResponseEntity.ok(teacherService.update(id, teacherDto));
  
    }
    @PostAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<TeacherDto> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(teacherService.delete(id));

    }



}
