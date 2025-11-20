package com.example.inmemoryeventsapi.dominio.ports.out;

import com.example.inmemoryeventsapi.dominio.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Puerto de salida para persistencia de eventos.
 * Define las operaciones necesarias sin acoplarse a la implementación (JPA,
 * MongoDB, etc.)
 */
public interface EventoRepositoryPort {
    Event guardar(Event event);

    Optional<Event> buscarPorId(Long id);

    Page<Event> buscarTodos(Pageable pageable);

    Page<Event> buscarConFiltros(String city, String category, LocalDate fechaInicio, Pageable pageable);

    boolean existePorNombre(String name);

    boolean existePorId(Long id);

    void eliminar(Long id);
}
