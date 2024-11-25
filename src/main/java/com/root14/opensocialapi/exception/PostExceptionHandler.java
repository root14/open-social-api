package com.root14.opensocialapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class PostExceptionHandler {
    @ExceptionHandler(value = PostException.class)
    public ResponseEntity<Object> handlerUserException(PostException postException, WebRequest request) {
        return ResponseEntity.status(postException.getHttpStatus()).body(postException.getErrorMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Object> handlerUserException(Exception ex, WebRequest request) {
        return ResponseEntity.status(500).body(ex.getMessage());
    }
}
