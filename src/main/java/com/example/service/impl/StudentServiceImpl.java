package com.example.service.impl;

import com.example.dto.teacher.TeacherDto;
import com.example.enums.Roles;
import com.example.exception.StudentNotFoundException;
import com.example.mapper.StudentMapper;
import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RefreshTokenRequestDto;
import com.example.dto.student.CreateStudentDto;
import com.example.dto.student.StudentDto;
import com.example.dto.student.UpdateStudentDto;
import com.example.entity.StudentEntity;
import com.example.repository.StudentRepository;
import com.example.repository.StudentTeacherRepository;
import com.example.security.JwtService;
import com.example.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentServiceImpl implements StudentService {

    private  final StudentMapper studentMapper;
    private  final StudentRepository studentRepository;
    private final StudentTeacherRepository studentTeacherRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;



    @Override
    public StudentDto createStudent(CreateStudentDto createStudentDto) {
        studentRepository.findByUsername(createStudentDto.getUsername())
                .ifPresent( st->{
                    throw new RuntimeException("Student" + st +" already exists");});

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities()
                .forEach(grantedAuthority -> {
                    log.info("Roles {}", grantedAuthority.getAuthority());

                });
        StudentEntity studentEntity = studentMapper.toStudentEntity(createStudentDto);
        String encoderPassword = passwordEncoder.encode(createStudentDto.getPassword());
        studentEntity.setPassword(encoderPassword);

        StudentEntity saveStudent = studentRepository.save(studentEntity);
        return studentMapper.toStudentDto(saveStudent);
    }

    @Override
    public StudentDto getById(Long id) {

        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Student not found " + id));
        return studentMapper.toStudentDto(studentEntity);
    }

    @Override
    public List<StudentDto> getAll() {
        return   studentRepository.findAll()
                .stream()
                .map(studentMapper::toStudentDto)
                .toList();


    }

    @Override
    public StudentDto update(Long id, UpdateStudentDto updateStudentDto) {
        StudentEntity studentEntity = studentRepository.findById(id)
            .orElseThrow(()->new StudentNotFoundException("Student not found " + id));
        StudentEntity updateStudent = studentMapper.toStudentEntity(updateStudentDto, studentEntity);
        return studentMapper.toStudentDto(studentRepository.save(updateStudent));

    }

    @Override
    public StudentDto delete(Long id) {
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(()->new StudentNotFoundException("Student not found " + id));
        studentRepository.delete(studentEntity);
        return studentMapper.toStudentDto(studentEntity);
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        StudentEntity studentEntity = studentRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Roles>  roles = new ArrayList<>();
        if (studentEntity.getId() == 0){
            roles.add(Roles.USER);
            roles.add(Roles.ADMIN);
        }

        String currentPassword = studentEntity.getPassword();
        String appliedPassword = loginRequest.getPassword();

        if(!passwordEncoder.matches(appliedPassword, currentPassword)){
            throw new RuntimeException("Wrong password");
        }
        StudentDto studentDto = studentMapper.toStudentDto(studentEntity);

        String accessToken = jwtService.generateToken(studentDto,roles );
        String refreshToken = jwtService.refreshToken(studentDto);



        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public LoginResponse refresh(RefreshTokenRequestDto request) {
        Long id = jwtService.validateRefreshToken(request.getRefreshToken());
        StudentEntity studentEntity = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        StudentDto studentDto = studentMapper.toStudentDto(studentEntity);
        String accessToken = jwtService.generateToken(studentDto, List.of());
        String refreshToken = jwtService.refreshToken(studentDto);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
