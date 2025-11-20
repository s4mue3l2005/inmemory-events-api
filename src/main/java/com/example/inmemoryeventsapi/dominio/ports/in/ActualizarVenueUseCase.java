package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Venue;

/**
 * Puerto de entrada para actualizar un venue.
 */
public interface ActualizarVenueUseCase {
    Venue ejecutar(Long id, Venue venue);
}
