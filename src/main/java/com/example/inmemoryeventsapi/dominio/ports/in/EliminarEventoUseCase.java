package com.example.inmemoryeventsapi.dominio.ports.in;

/**
 * Puerto de entrada para eliminar un evento.
 */
public interface EliminarEventoUseCase {
    void ejecutar(Long id);
}
