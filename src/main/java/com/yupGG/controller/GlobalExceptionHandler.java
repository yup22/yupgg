package com.yupGG.controller;

import groovyjarjarpicocli.CommandLine;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CommandLine.DuplicateNameException.class)
    public ResponseEntity<String> handleDuplicateNameException(CommandLine.DuplicateNameException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
