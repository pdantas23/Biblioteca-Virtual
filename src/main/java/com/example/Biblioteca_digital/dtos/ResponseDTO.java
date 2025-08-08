package com.example.Biblioteca_digital.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T>{
    private LocalDateTime timestamp;
    private String message;
    private HttpStatus status;
    private T data;
}
