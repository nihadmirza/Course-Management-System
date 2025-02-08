package com.example.mapper;
 
import com.example.dto.student.CreateStudentDto;
import com.example.dto.student.StudentDto;
import com.example.dto.student.UpdateStudentDto;
import com.example.entity.StudentEntity;
import org.mapstruct.*;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)

public interface StudentMapper {


    StudentEntity toStudentEntity(CreateStudentDto studentDto);

    StudentDto toStudentDto(StudentEntity saveStudent);

    StudentEntity toStudentEntity(UpdateStudentDto updateStudentDto,@MappingTarget StudentEntity studentEntity);
}
