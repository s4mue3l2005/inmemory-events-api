package com.example.inmemoryeventsapi.dominio.exception;

/**
 * Excepción de dominio para recursos no encontrados.
 */
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
