package com.example.dto.teacher;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UpdateTeacherDto {
     String name;
     String surname;
     String email;
     String username;
     Integer lessonCount;

}
