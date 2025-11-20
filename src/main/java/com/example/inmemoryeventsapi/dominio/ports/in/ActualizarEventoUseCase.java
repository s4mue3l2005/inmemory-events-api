package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Event;

/**
 * Puerto de entrada para actualizar un evento.
 */
public interface ActualizarEventoUseCase {
    Event ejecutar(Long id, Event event);
}
