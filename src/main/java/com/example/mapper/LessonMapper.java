package com.example.mapper;

import com.example.dto.lesson.CreateLessonDto;
import com.example.dto.lesson.LessonDto;
import com.example.dto.lesson.UpdateLessonDto;
import com.example.entity.LessonEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface LessonMapper {


    LessonEntity toLessonEntity(CreateLessonDto createLessonDto);

    LessonDto toLessonDto(LessonEntity lessonEntityDb);

    LessonEntity toLessonEntity(UpdateLessonDto updateLessonDto, @MappingTarget  LessonEntity lessonEntity);
}
