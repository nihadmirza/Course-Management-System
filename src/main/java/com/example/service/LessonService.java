package com.example.service;

import com.example.dto.lesson.CreateLessonDto;
import com.example.dto.lesson.LessonDto;
import com.example.dto.lesson.UpdateLessonDto;

import java.util.List;

public interface LessonService  {

      LessonDto upddateLesson(Long id, UpdateLessonDto updateLessonDto) ;

      LessonDto createLesson(CreateLessonDto createLessonDto) ;

      LessonDto deleteLeesson(Long id);

      LessonDto deactiveLesson(Long id);

      List<LessonDto> GetAllLessons();

      LessonDto getById(Long id);
}
