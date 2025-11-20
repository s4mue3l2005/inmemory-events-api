package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Venue;

import java.util.Optional;

/**
 * Puerto de entrada para obtener un venue por ID.
 */
public interface ObtenerVenueUseCase {
    Optional<Venue> ejecutar(Long id);
}
