package com.chisimdi.auth_service.Exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiError>existsHandler(UserAlreadyExistsException e){
        ApiError apiError=new ApiError(e.getMessage(),400);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError>notFoundHandler(ResourceNotFoundException e){
        ApiError apiError=new ApiError(e.getMessage(), 404);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError2>validationHandler(MethodArgumentNotValidException e){
        ApiError2 apiError2=new ApiError2("validation error",400);
        for(int x=0;x<e.getFieldErrorCount();x++){
            apiError2.getErrors().put(e.getBindingResult().getFieldError().getField(),e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError2);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>generalHandler(Exception e){
        ApiError apiError=new ApiError("Internal Sever Error",500);
        log.error("internal Server Error {}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(apiError);
    }
@ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ApiError>credentialsHandler(InvalidCredentialsException e){
        ApiError apiError=new ApiError(e.getMessage(),400);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
}
}
