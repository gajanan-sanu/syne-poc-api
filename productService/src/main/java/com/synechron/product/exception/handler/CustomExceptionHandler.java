package com.synechron.product.exception.handler;

import com.synechron.product.exception.ProductNotFoundException;
import com.synechron.product.model.APIEndpointError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ProductNotFoundException.class})
    ResponseEntity<?> offerNotValidHandler(Exception exc, ServletWebRequest request) {

        APIEndpointError apiEndpointError = new APIEndpointError();

        apiEndpointError.setTimeStamp(LocalDateTime.now());
        apiEndpointError.setPathUri(request.getDescription(true));
        apiEndpointError.setStatus(HttpStatus.BAD_REQUEST);
        apiEndpointError.setErrors(Arrays.asList(exc.getMessage()));

        return new ResponseEntity(apiEndpointError, new HttpHeaders(), apiEndpointError.getStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> errors = fieldErrors.stream()
                .map(err -> err.getField() + " : " + err.getDefaultMessage())
                .collect(Collectors.toList());


        APIEndpointError apiEndpointError = new APIEndpointError();
        apiEndpointError.setStatus(HttpStatus.BAD_REQUEST);
        apiEndpointError.setTimeStamp(LocalDateTime.now());
        apiEndpointError.setPathUri(request.getDescription(false));
        apiEndpointError.setErrors(errors);

        return new ResponseEntity<>(apiEndpointError, headers, apiEndpointError.getStatus());
    }
}
