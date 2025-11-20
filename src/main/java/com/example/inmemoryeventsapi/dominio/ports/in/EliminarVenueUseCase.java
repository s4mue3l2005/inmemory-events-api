package com.example.inmemoryeventsapi.dominio.ports.in;

/**
 * Puerto de entrada para eliminar un venue.
 */
public interface EliminarVenueUseCase {
    void ejecutar(Long id);
}
