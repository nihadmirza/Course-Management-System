package com.example.service.impl;

import com.example.enums.Roles;
import com.example.exception.TeacherNotFoundException;
import com.example.mapper.TeacherMapper;
import com.example.dto.LoginRequest;
import com.example.dto.LoginResponse;
import com.example.dto.RefreshTokenRequestDto;
import com.example.dto.teacher.CreateTeacherDto;
import com.example.dto.teacher.TeacherDto;
import com.example.dto.teacher.UpdateTeacherDto;
import com.example.entity.TeacherEntity;
import com.example.repository.TeacherRepository;
import com.example.security.JwtService;
import com.example.service.TeacherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Slf4j
@Service
@RequiredArgsConstructor

public class TeacherServiceImpl implements TeacherService {

    private  final TeacherMapper teacherMapper;
    private  final TeacherRepository teacherRepository;
    private final JwtService jwtService;
    private  final PasswordEncoder passwordEncoder;




    @Override
    public TeacherDto createTeacher(CreateTeacherDto teacherDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities()
                .forEach(grantedAuthority -> {
                    log.info("Roles {}", grantedAuthority.getAuthority());

                });

        teacherRepository.findByUsername(teacherDto.getUsername())
                .ifPresent( user ->{
                    throw new RuntimeException("Teacher username already exists");
                });
        TeacherEntity teacherEntity = teacherMapper.toTeacherEntity(teacherDto);
        String encodedPassword = passwordEncoder.encode(teacherDto.getPassword());

        teacherEntity.setPassword(encodedPassword);     //(passwordEncoder.encode(teacherDto.getPassword()));
        TeacherEntity saveTeacher = teacherRepository.save(teacherEntity);

        return teacherMapper.toTeacherDto(saveTeacher);
    }
    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        TeacherEntity teacherEntity = teacherRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User is not found" ));

        List<Roles>  roles = new ArrayList<>();

        if (teacherEntity.getId() == 0){
            roles.add(Roles.USER);
            roles.add(Roles.ADMIN);
        }

        String currentPassword = teacherEntity.getPassword();
        String appliedPassword = loginRequest.getPassword();
        if (!passwordEncoder.matches(appliedPassword,currentPassword)) {
            throw  new RuntimeException("Wrong password");
        }


        TeacherDto  teacherDto = teacherMapper.toTeacherDto(teacherEntity);


        String accessToken = jwtService.generateToken(teacherDto, roles);
        String refreshToken = jwtService.refreshToken(teacherDto);

        return   LoginResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();
    }
    @Override
    public LoginResponse refresh(RefreshTokenRequestDto request) {
        Long id = jwtService.validateRefreshToken(request.getRefreshToken());
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow( ()-> new RuntimeException("User is not found"));

        TeacherDto teacherDto = teacherMapper.toTeacherDto(teacherEntity);
        String accessToken = jwtService.generateToken(teacherDto, List.of());
        String refreshToken = jwtService.refreshToken(teacherDto);

        return   LoginResponse.builder()
                .refreshToken(refreshToken)
                .accessToken(accessToken)
                .build();

    }

@Override
    public TeacherDto getById(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(()->new TeacherNotFoundException("Teacher not found " + id));
        return teacherMapper.toTeacherDto(teacherEntity);
    }

    @Override
    public List<TeacherDto> getAll() {
        return   teacherRepository.findAll()
                .stream()
                .map(teacherMapper::toTeacherDto)
                .toList();
    }

    @Override
    public TeacherDto update(Long id, UpdateTeacherDto updateTeacherDto) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
            .orElseThrow(()->new TeacherNotFoundException("Teacher not found " + id));
        TeacherEntity updateTeacher = teacherMapper.toTeacherEntity(updateTeacherDto, teacherEntity);
        return teacherMapper.toTeacherDto(teacherRepository.save(updateTeacher));

    }

    @Override
    public TeacherDto delete(Long id) {
        TeacherEntity teacherEntity = teacherRepository.findById(id)
                .orElseThrow(()->new TeacherNotFoundException("Teacher not found " + id));
        teacherRepository.delete(teacherEntity);
        return teacherMapper.toTeacherDto(teacherEntity);
    }
}


