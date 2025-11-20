package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Event;

import java.util.Optional;

/**
 * Puerto de entrada para obtener un evento por ID.
 */
public interface ObtenerEventoUseCase {
    Optional<Event> ejecutar(Long id);
}
