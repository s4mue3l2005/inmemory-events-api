package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Venue;

import java.util.List;

/**
 * Puerto de entrada para listar todos los venues.
 */
public interface ListarVenuesUseCase {
    List<Venue> ejecutar();
}
