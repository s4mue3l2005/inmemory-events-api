package com.example.inmemoryeventsapi.controller;

import com.example.inmemoryeventsapi.dto.VenueDTO;
import com.example.inmemoryeventsapi.service.VenueService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/venues")
public class VenueController {

    private final VenueService service;

    public VenueController(VenueService service) {
        this.service = service;
    }

    // Obtener todos los venues
    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Obtener un venue por ID
    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Crear nuevo venue
    @PostMapping
    public ResponseEntity<VenueDTO> create(@RequestBody VenueDTO venue) {
        if (venue.getName() == null || venue.getName().isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.save(venue));
    }

    // Actualizar venue existente
    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> update(@PathVariable Long id, @RequestBody VenueDTO venue) {
        return service.update(id, venue)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Eliminar venue
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        return service.delete(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}