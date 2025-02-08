package com.example.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
 @FieldDefaults(level = AccessLevel.PRIVATE)
@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "lesson")
public class LessonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, name ="title")
    String title;

    @Column(nullable = false, name = "is_active")
    Boolean isActive = true;

    @CreationTimestamp
    @Column(nullable = false, name = "created_At")
    LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_At")
    LocalDateTime updatedAt;

}
