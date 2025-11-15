package com.chisimdi.result_service.exceptions;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.apache.hc.core5.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{
    private static final Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> notFoundHandler(ResourceNotFoundException e){
        ApiError apiError=new ApiError(404,e.getMessage());
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiError>feignNotFoundHandler(FeignException.NotFound e){
        ApiError apiError=new ApiError(404,"Resource not found");
        log.warn("Feign Exception Error {}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>generalHandler(Exception e){
        ApiError apiError=new ApiError(500,"Internal Server Error");
        log.error("Internal Server Error {}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(apiError);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError2>validationHandler(MethodArgumentNotValidException e){
        ApiError2 apiError2=new ApiError2("validation Error",400);
        log.warn("Validation Error {}",e.getMessage(),e);
        for(int x=0;x< e.getFieldErrorCount();x++){
            apiError2.getErrors().put(e.getBindingResult().getFieldError().getField(),e.getBindingResult().getFieldError().getDefaultMessage());
        }
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(apiError2);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError>violationHandler(ConstraintViolationException e){

        ApiError apiError2= new ApiError(400,e.getMessage());
        log.warn("Violation Error {}",e.getMessage(),e);
        return ResponseEntity.status(org.springframework.http.HttpStatus.BAD_REQUEST).body(apiError2);

    }
}
