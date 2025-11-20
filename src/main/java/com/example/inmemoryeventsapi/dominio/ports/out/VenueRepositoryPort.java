package com.example.inmemoryeventsapi.dominio.ports.out;

import com.example.inmemoryeventsapi.dominio.model.Venue;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de venues.
 * Define las operaciones necesarias sin acoplarse a la implementación (JPA,
 * MongoDB, etc.)
 */
public interface VenueRepositoryPort {
    Venue guardar(Venue venue);

    Optional<Venue> buscarPorId(Long id);

    List<Venue> buscarTodos();

    boolean existePorId(Long id);

    void eliminar(Long id);
}
