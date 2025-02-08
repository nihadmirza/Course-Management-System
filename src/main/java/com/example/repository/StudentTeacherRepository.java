package com.example.repository;

import com.example.entity.StudentTeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentTeacherRepository extends JpaRepository<StudentTeacherEntity, Long> {



}
