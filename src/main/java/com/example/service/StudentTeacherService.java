package com.example.service;

import com.example.dto.studentTeacher.CreateStudentTeacherDto;
import com.example.dto.studentTeacher.StudentTeacherDto;

public interface StudentTeacherService {
    StudentTeacherDto create(CreateStudentTeacherDto createStudentTeacherDto);
}
