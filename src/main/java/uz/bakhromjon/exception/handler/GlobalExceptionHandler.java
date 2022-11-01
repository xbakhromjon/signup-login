package uz.bakhromjon.exception.handler;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import uz.bakhromjon.exception.exception.UniversalException;
import uz.bakhromjon.exception.exception.ValidationException;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UniversalException.class)
    public ResponseEntity<?> UniversalExceptionHandler(UniversalException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<?> ValidationExceptionHandler(ValidationException exception, WebRequest webRequest) {
        return new ResponseEntity<>(exception.getMessage(), exception.getStatus());
    }


    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
    }


}
