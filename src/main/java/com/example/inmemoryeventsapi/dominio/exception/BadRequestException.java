package com.example.inmemoryeventsapi.dominio.exception;

/**
 * Excepción de dominio para peticiones inválidas.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
