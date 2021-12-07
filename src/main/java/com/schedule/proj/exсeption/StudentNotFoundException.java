package com.schedule.proj.exсeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "student not found")
public class StudentNotFoundException extends RuntimeException{
    public StudentNotFoundException(String s) {
    }
}
