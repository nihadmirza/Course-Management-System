package com.example.dto.studentTeacher;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentTeacherDto {
    Long id;

    Long student_id;

    Long teacher_id;
}
