package com.example.inmemoryeventsapi.controller;

import com.example.inmemoryeventsapi.dto.EventDTO;
import com.example.inmemoryeventsapi.service.EventService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService service;

    public EventController(EventService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Page<EventDTO>> getAll(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String fechaInicio,
            @PageableDefault(size = 10, sort = "date") Pageable pageable
    ) {
        // Si hay filtros, usar el método con filtro
        if (city != null || category != null || fechaInicio != null) {
            return ResponseEntity.ok(service.findByFilters(city, category, fechaInicio, pageable));
        }
        // Si no hay filtros, usar paginación simple
        return ResponseEntity.ok(service.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EventDTO> create(@Valid @RequestBody EventDTO event) {
        return ResponseEntity.ok(service.save(event));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EventDTO> update(@PathVariable Long id, @Valid @RequestBody EventDTO event) {
        return service.update(id, event)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}