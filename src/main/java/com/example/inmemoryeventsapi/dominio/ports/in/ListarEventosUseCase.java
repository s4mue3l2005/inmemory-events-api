package com.example.inmemoryeventsapi.dominio.ports.in;

import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;

/**
 * Puerto de entrada para listar eventos con filtros y paginación.
 */
public interface ListarEventosUseCase {
    Page<Event> ejecutar(Pageable pageable);

    Page<Event> ejecutarConFiltros(String city, String category, String fechaInicio, Pageable pageable);
}
