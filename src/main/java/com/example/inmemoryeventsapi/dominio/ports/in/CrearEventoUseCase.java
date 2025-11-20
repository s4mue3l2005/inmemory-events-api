package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Event;

/**
 * Puerto de entrada para crear un evento.
 */
public interface CrearEventoUseCase {
    Event ejecutar(Event event);
}
