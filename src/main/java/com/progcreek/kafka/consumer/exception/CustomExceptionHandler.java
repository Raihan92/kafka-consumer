package com.progcreek.kafka.consumer.exception;

import com.progcreek.kafka.consumer.global.Reply;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed", errors,null);
        return new ResponseEntity(reply, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullPointerException.class)
    public final ResponseEntity<Object> handleNullPointerException(NullPointerException ex, WebRequest request) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, HttpStatus.BAD_REQUEST.value(), "Failed", errors,null);
        return new ResponseEntity(reply, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<String> handleRunTimeException(RuntimeException ex) {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());
        Reply reply = new Reply(false, HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed", errors,null);
        return new ResponseEntity(reply, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
