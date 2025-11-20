package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Venue;

/**
 * Puerto de entrada para crear un venue.
 */
public interface CrearVenueUseCase {
    Venue ejecutar(Venue venue);
}
