package es.ecommerce.prices.api.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import es.ecommerce.prices.api.dto.ErrorDTO;
import es.ecommerce.prices.core.exception.BrandProductPriceNotFoundException;

import javax.validation.ConstraintViolationException;

//TODO se usa el mismo modelMapper ???

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
    private final ModelMapper modelMapper;

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUnknownError(
            RuntimeException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                modelMapper.map(ex, ErrorDTO.class),
                new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<Object> handleValidationError(
            ConstraintViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                modelMapper.map(ex, ErrorDTO.class),
                new HttpHeaders(),
                HttpStatus.BAD_REQUEST,
                request);
    }

    @ExceptionHandler(value = {BrandProductPriceNotFoundException.class})
    protected ResponseEntity<Object> handleNotFound(
            BrandProductPriceNotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                modelMapper.map(ex, ErrorDTO.class),
                new HttpHeaders(),
                HttpStatus.NOT_FOUND,
                request);
    }

}