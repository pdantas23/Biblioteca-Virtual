package com.example.Biblioteca_digital.exceptionhandler;

import com.example.Biblioteca_digital.dtos.ResponseDTO;
import com.example.Biblioteca_digital.exceptions.*;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class )
    public ResponseEntity<ResponseDTO<Map<String, String>>> handleValidationException(MethodArgumentNotValidException exception){
        Map<String, String> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        ResponseDTO<Map<String, String>> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Erro de validação nos dados enviados",
                HttpStatus.BAD_REQUEST,
                errors
        );

        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity<ResponseDTO<String>> handleInvalidFormat(InvalidFormatException exception) {

        ResponseDTO<String> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                "Formato inválido em um dos campos",
                HttpStatus.BAD_REQUEST,
                null
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ResponseDTO<Void>> handleEventNotFoundException(EventNotFoundException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ResponseDTO<Void>> handleDuplicateResourceException(DuplicateResourceException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.CONFLICT,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseDTO<Void>> handleForbiddenException(ForbiddenException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.FORBIDDEN,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InternalServerErrorException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInternalServerErrorException(InternalServerErrorException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInvalidDataException(InvalidDataException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomInvalidFormatException.class)
    public ResponseEntity<ResponseDTO<Void>> handleInvalidFormatException(CustomInvalidFormatException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<ResponseDTO<Void>> handleUnathorizedException(UnathorizedException exception, HttpServletRequest request) {
        ResponseDTO<Void> responseDTO = new ResponseDTO<>(
                LocalDateTime.now(),
                exception.getMessage(),
                HttpStatus.UNAUTHORIZED,
                null
        );
        return new ResponseEntity<>(responseDTO, HttpStatus.UNAUTHORIZED);
    }
}
