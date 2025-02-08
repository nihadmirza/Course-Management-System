package com.example.dto.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateStudentDto {

    String name;
    String surname;
    String  address;
    String email;
    String username;
    String  phoneNumber;
}
