package com.example.exception;

public class TeacherNotFoundException extends RuntimeException{
    public TeacherNotFoundException(String s) {
        super(s);
    }
}
