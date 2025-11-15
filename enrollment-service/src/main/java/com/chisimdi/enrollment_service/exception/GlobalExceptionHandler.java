package com.chisimdi.enrollment_service.exception;

import feign.FeignException;
import jakarta.validation.ConstraintViolationException;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
private final static Logger log= LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @ExceptionHandler(CapacityException.class)
    public ResponseEntity<ApiError>capacityHandler(CapacityException e){
        ApiError apiError=new ApiError(500,e.getMessage());
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(apiError);

    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError>notFoundHandler(ResourceNotFoundException e){
        ApiError apiError=new ApiError(404,e.getMessage());
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError>generalHandler(Exception e){
        ApiError apiError=new ApiError(500,"Internal Server Error");
log.error("Internal server Error {}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.SC_INTERNAL_SERVER_ERROR).body(apiError);
    }
    @ExceptionHandler(FeignException.NotFound.class)
    public ResponseEntity<ApiError>feignNotFoundException(FeignException.NotFound e){
        ApiError apiError=new ApiError(404, "Resource Not Found");
        log.warn("Feign Not found Exception {}",e.getMessage(),e);
        return ResponseEntity.status(HttpStatus.SC_NOT_FOUND).body(apiError);
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
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiError>unauthorizedHandler(AuthorizationDeniedException e){
        ApiError apiError=new ApiError(401,"Access unauthorised");
        log.warn("unauthorized access {}",e.getMessage(),e);
        return ResponseEntity.status(org.springframework.http.HttpStatus.UNAUTHORIZED).body(apiError);
    }
}
