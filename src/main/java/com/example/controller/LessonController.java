package com.example.controller;

import com.example.dto.lesson.CreateLessonDto;
import com.example.dto.lesson.LessonDto;
import com.example.dto.lesson.UpdateLessonDto;
import com.example.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson")
@RequiredArgsConstructor
@Slf4j
public class LessonController {
    private final LessonService lessonService;


    @PostAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<LessonDto > createLesson(@Valid  @RequestBody CreateLessonDto createLessonDto){
        log.info("Request started");
        return ResponseEntity.ok(  lessonService.createLesson(createLessonDto));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @PatchMapping("update{id}")
    public ResponseEntity<LessonDto> updateLesson(@RequestParam  Long id, @Valid @RequestBody UpdateLessonDto updateLessonDto){
        log.info("update started");
        return ResponseEntity.ok(lessonService.upddateLesson(id, updateLessonDto ));
    }

    @PostAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<LessonDto> deleteLesson(@RequestParam  Long id){
        log.info("delete started");
        return  ResponseEntity.ok(lessonService.deleteLeesson(id));
    }

    @PatchMapping("deactive")
    public  ResponseEntity<LessonDto>  deactiveLesson(@RequestParam Long id){
            return ResponseEntity.ok(lessonService.deactiveLesson(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonDto> getById(@PathVariable("id") Long id) {
        log.info("getById started");
        return ResponseEntity.ok(lessonService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<LessonDto>> getAllLessons(){
        log.info("getAllLessons started");
        return ResponseEntity.ok(lessonService.GetAllLessons());
    }

}
