package com.example.service.impl;

import com.example.exception.LessonNotFoundException;
import com.example.mapper.LessonMapper;
import com.example.dto.lesson.CreateLessonDto;
import com.example.dto.lesson.LessonDto;
import com.example.dto.lesson.UpdateLessonDto;
import com.example.entity.LessonEntity;
import com.example.repository.LessonRepository;
import com.example.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonMapper lessonMapper;


    @Override
    public LessonDto upddateLesson(Long id, UpdateLessonDto updateLessonDto) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow( ()-> new LessonNotFoundException("lesson not found  by id: "+ id));

        LessonEntity updateLesson  = lessonMapper.toLessonEntity(updateLessonDto, lessonEntity) ;
        LessonEntity updatedLessonDB = lessonRepository.save(updateLesson);
        return lessonMapper.toLessonDto(updatedLessonDB);

    }

    @Override
    public LessonDto createLesson(CreateLessonDto createLessonDto) {
        LessonEntity lessonEntity = lessonMapper.toLessonEntity(createLessonDto);
        LessonEntity lessonEntityDb = lessonRepository.save(lessonEntity);
        return lessonMapper.toLessonDto(lessonEntityDb);
    }

    @Override
    public LessonDto deleteLeesson(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(()-> new LessonNotFoundException("lesson not found  by id: "+ id));
          lessonRepository.delete(lessonEntity);
        return lessonMapper.toLessonDto(lessonEntity);
    }

    @Override
    public LessonDto deactiveLesson(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(()-> new LessonNotFoundException("lesson not found  by id: "+ id));
        lessonEntity.setIsActive(false);
        lessonRepository.save(lessonEntity);
        return lessonMapper.toLessonDto(lessonEntity);
    }

    @Override
    public List<LessonDto> GetAllLessons() {
         return lessonRepository.findAll()
                 .stream()
                 .map(lessonMapper::toLessonDto)
                 .toList();

    }

    @Override
    public LessonDto getById(Long id) {
        LessonEntity lessonEntity = lessonRepository.findById(id)
                .orElseThrow(()-> new LessonNotFoundException("lesson not found  by id: "+ id));
        return lessonMapper.toLessonDto(lessonEntity);
    }
}
