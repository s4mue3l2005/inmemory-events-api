package com.example.inmemoryeventsapi.dominio.exception;

/**
 * Excepción de dominio para conflictos (ej: duplicados).
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
