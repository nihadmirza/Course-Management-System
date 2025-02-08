package com.example.mapper;

import com.example.dto.teacher.CreateTeacherDto;
import com.example.dto.teacher.TeacherDto;
import com.example.dto.teacher.UpdateTeacherDto;
import com.example.entity.TeacherEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface TeacherMapper {



    TeacherDto toTeacherDto(TeacherEntity saveTeacher);

    TeacherEntity toTeacherEntity(CreateTeacherDto teacherDto);

    TeacherEntity toTeacherEntity(UpdateTeacherDto updateTeacherDto,@MappingTarget TeacherEntity teacherEntity);
}
