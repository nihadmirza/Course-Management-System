package com.example.dto.student;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateStudentDto {

    String name;
    String surname;
    String username;
    String email;
    String password;
    String  address;
    String  phoneNumber;
}
