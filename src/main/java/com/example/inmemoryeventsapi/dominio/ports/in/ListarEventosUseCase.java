package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Puerto de entrada para listar eventos con filtros y paginación.
 */
public interface ListarEventosUseCase {
    Page<Event> ejecutar(Pageable pageable);

    Page<Event> ejecutarConFiltros(String city, String category, String fechaInicio, Pageable pageable);
}
