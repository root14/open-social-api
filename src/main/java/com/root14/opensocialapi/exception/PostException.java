package com.root14.opensocialapi.exception;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class PostException extends Throwable {
    private ErrorType errorType;
    private HttpStatus httpStatus;
    private String errorMessage;
    private Exception exception;
}
