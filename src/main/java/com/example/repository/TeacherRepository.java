package com.example.repository;

import com.example.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface TeacherRepository extends JpaRepository<TeacherEntity, Long> {
    Optional<TeacherEntity>findByUsername(String username);

}
