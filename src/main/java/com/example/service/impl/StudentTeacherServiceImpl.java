package com.example.service.impl;

import com.example.mapper.StudentTeacherMapper;
import com.example.dto.studentTeacher.CreateStudentTeacherDto;
import com.example.dto.studentTeacher.StudentTeacherDto;
import com.example.entity.StudentTeacherEntity;
import com.example.repository.StudentRepository;
import com.example.repository.StudentTeacherRepository;
import com.example.repository.TeacherRepository;
import com.example.service.StudentTeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentTeacherServiceImpl implements StudentTeacherService {

    private final StudentTeacherMapper studentTeacherMapper;
    private final StudentTeacherRepository studentTeacherRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;

    @Override
    public StudentTeacherDto create(CreateStudentTeacherDto createStudentTeacherDto) {

        StudentTeacherEntity studentTeacher = new StudentTeacherEntity();
        studentTeacher.setStudent(studentRepository.findById(createStudentTeacherDto.getStudent_id())
                .orElseThrow(() -> new RuntimeException("Student not found")));
        studentTeacher.setTeacher(teacherRepository.findById(createStudentTeacherDto.getTeacher_id())
                .orElseThrow(() -> new RuntimeException("Teacher not found")));


        StudentTeacherEntity save = studentTeacherRepository.save(studentTeacher);
        log.info("Student {}",save.getStudent().getAddress());

        return studentTeacherMapper.toStudentTeacherDto(studentTeacherRepository.save(studentTeacher));
    }
}
