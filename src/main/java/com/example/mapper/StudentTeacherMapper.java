package com.example.mapper;


import com.example.dto.studentTeacher.StudentTeacherDto;
import com.example.entity.StudentTeacherEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)


public interface StudentTeacherMapper {


    @Mapping(target = "student_id", source = "student.id")
    @Mapping(target = "teacher_id", source = "teacher.id")
     StudentTeacherDto toStudentTeacherDto(StudentTeacherEntity save);
}
