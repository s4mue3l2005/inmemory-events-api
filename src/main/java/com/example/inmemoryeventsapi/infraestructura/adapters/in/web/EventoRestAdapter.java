package com.example.inmemoryeventsapi.infraestructura.adapters.in.web;

import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;
import com.example.inmemoryeventsapi.dominio.ports.in.*;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.EventDTO;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper.EventoDTOMapper;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper.PageMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Adaptador REST para eventos.
 * Expone los endpoints HTTP y delega en los casos de uso.
 */
@RestController
@RequestMapping("/events")
public class EventoRestAdapter {

    private final CrearEventoUseCase crearEventoUseCase;
    private final ActualizarEventoUseCase actualizarEventoUseCase;
    private final EliminarEventoUseCase eliminarEventoUseCase;
    private final ObtenerEventoUseCase obtenerEventoUseCase;
    private final ListarEventosUseCase listarEventosUseCase;
    private final EventoDTOMapper eventoDTOMapper;

    public EventoRestAdapter(CrearEventoUseCase crearEventoUseCase,
            ActualizarEventoUseCase actualizarEventoUseCase,
            EliminarEventoUseCase eliminarEventoUseCase,
            ObtenerEventoUseCase obtenerEventoUseCase,
            ListarEventosUseCase listarEventosUseCase,
            EventoDTOMapper eventoDTOMapper) {
        this.crearEventoUseCase = crearEventoUseCase;
        this.actualizarEventoUseCase = actualizarEventoUseCase;
        this.eliminarEventoUseCase = eliminarEventoUseCase;
        this.obtenerEventoUseCase = obtenerEventoUseCase;
        this.listarEventosUseCase = listarEventosUseCase;
        this.eventoDTOMapper = eventoDTOMapper;
    }

    @GetMapping
    public ResponseEntity<org.springframework.data.domain.Page<EventDTO>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String fechaInicio,
            @PageableDefault(size = 10, sort = "date") org.springframework.data.domain.Pageable springPageable) {

        // Convertir Pageable de Spring a dominio
        Pageable domainPageable = PageMapper.toDomain(springPageable);

        Page<Event> events;

        // Si hay filtros, usar el método con filtro
        if (city != null || category != null || fechaInicio != null) {
            events = listarEventosUseCase.ejecutarConFiltros(city, category, fechaInicio, domainPageable);
        } else {
            events = listarEventosUseCase.ejecutar(domainPageable);
        }

        // Convertir eventos a DTOs
        List<EventDTO> dtos = events.getContent().stream()
                .map(eventoDTOMapper::toDTO)
                .toList();

        // Convertir Page del dominio a Page de Spring para la respuesta
        org.springframework.data.domain.Page<EventDTO> springPage = PageMapper.toSpring(
                new Page<>(dtos, events.getPageNumber(), events.getPageSize(), events.getTotalElements()));

        return ResponseEntity.ok(springPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return obtenerEventoUseCase.obtener(id)
                .map(eventoDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.example.inmemoryeventsapi.dominio.exception.NotFoundException(
                        "Evento no encontrado con id: " + id));
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventDTO eventDTO) {
        Event event = eventoDTOMapper.toDomain(eventDTO);
        Event created = crearEventoUseCase.ejecutar(event);
        return ResponseEntity.ok(eventoDTOMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO eventDTO) {
        Event event = eventoDTOMapper.toDomain(eventDTO);
        Event updated = actualizarEventoUseCase.ejecutar(id, event);
        return ResponseEntity.ok(eventoDTOMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    @org.springframework.security.access.prepost.PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eliminarEventoUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
