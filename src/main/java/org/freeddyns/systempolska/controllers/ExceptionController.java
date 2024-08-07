package org.freeddyns.systempolska.controllers;

import org.freeddyns.systempolska.exception.WrongFileFormatException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(WrongFileFormatException.class)
    public ResponseEntity<String> wrongFileFormatException(WrongFileFormatException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }
}
