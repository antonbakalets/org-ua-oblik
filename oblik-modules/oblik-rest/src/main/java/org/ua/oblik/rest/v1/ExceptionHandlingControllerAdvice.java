package org.ua.oblik.rest.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.ua.oblik.service.BusinessConstraintException;
import org.ua.oblik.service.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFound(NotFoundException e) {
        return ResponseEntity.notFound().build(); // 404
    }

    @ExceptionHandler(BusinessConstraintException.class)
    public ResponseEntity<String> handleNotFound(BusinessConstraintException e) {
        return  ResponseEntity.badRequest().body(e.getMessage()); // 400
    }
}
