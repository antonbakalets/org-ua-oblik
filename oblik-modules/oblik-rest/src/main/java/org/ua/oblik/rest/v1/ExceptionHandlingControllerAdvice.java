package org.ua.oblik.rest.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return  ResponseEntity.status(HttpStatus.GONE).body(e.getMessage()); // 410
    }

    @ExceptionHandler(BusinessConstraintException.class)
    public ResponseEntity<String> handleNotFound(BusinessConstraintException e) {
        return  ResponseEntity.badRequest().body(e.getMessage()); // 400
    }
}
