package com.example.inmemoryeventsapi.infraestructura.adapters.in.web;

import com.example.inmemoryeventsapi.dominio.model.Venue;
import com.example.inmemoryeventsapi.dominio.ports.in.*;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.VenueDTO;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper.VenueDTOMapper;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Adaptador REST para venues.
 * Expone los endpoints HTTP y delega en los casos de uso.
 */
@RestController
@RequestMapping("/venues")
public class VenueRestAdapter {

    private final CrearVenueUseCase crearVenueUseCase;
    private final ActualizarVenueUseCase actualizarVenueUseCase;
    private final EliminarVenueUseCase eliminarVenueUseCase;
    private final ObtenerVenueUseCase obtenerVenueUseCase;
    private final ListarVenuesUseCase listarVenuesUseCase;
    private final VenueDTOMapper venueDTOMapper;

    public VenueRestAdapter(CrearVenueUseCase crearVenueUseCase,
            ActualizarVenueUseCase actualizarVenueUseCase,
            EliminarVenueUseCase eliminarVenueUseCase,
            ObtenerVenueUseCase obtenerVenueUseCase,
            ListarVenuesUseCase listarVenuesUseCase,
            VenueDTOMapper venueDTOMapper) {
        this.crearVenueUseCase = crearVenueUseCase;
        this.actualizarVenueUseCase = actualizarVenueUseCase;
        this.eliminarVenueUseCase = eliminarVenueUseCase;
        this.obtenerVenueUseCase = obtenerVenueUseCase;
        this.listarVenuesUseCase = listarVenuesUseCase;
        this.venueDTOMapper = venueDTOMapper;
    }

    @GetMapping
    public ResponseEntity<List<VenueDTO>> getAll() {
        List<Venue> venues = listarVenuesUseCase.ejecutar();
        List<VenueDTO> dtos = venues.stream()
                .map(venueDTOMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VenueDTO> getById(@PathVariable Long id) {
        return obtenerVenueUseCase.obtener(id)
                .map(venueDTOMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<VenueDTO> create(@Valid @RequestBody VenueDTO venueDTO) {
        Venue venue = venueDTOMapper.toDomain(venueDTO);
        Venue created = crearVenueUseCase.ejecutar(venue);
        return ResponseEntity.ok(venueDTOMapper.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VenueDTO> update(@PathVariable Long id, @Valid @RequestBody VenueDTO venueDTO) {
        Venue venue = venueDTOMapper.toDomain(venueDTO);
        Venue updated = actualizarVenueUseCase.ejecutar(id, venue);
        return ResponseEntity.ok(venueDTOMapper.toDTO(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        eliminarVenueUseCase.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
