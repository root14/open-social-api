package com.root14.opensocialapi.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<Object> handlerUserException(UserException userException, WebRequest request) {
        //todo lombok can prove non-english error message, fix-it
        return ResponseEntity.status(userException.getHttpStatus()).body(userException.getErrorMessage());
    }
}
