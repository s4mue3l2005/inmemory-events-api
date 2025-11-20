package com.example.inmemoryeventsapi.aplicacion.usecase;

import com.example.inmemoryeventsapi.dominio.exception.NotFoundException;
import com.example.inmemoryeventsapi.dominio.model.Venue;
import com.example.inmemoryeventsapi.dominio.ports.in.*;
import com.example.inmemoryeventsapi.dominio.ports.out.VenueRepositoryPort;

import java.util.List;
import java.util.Optional;

/**
 * Implementación de los casos de uso de venues.
 * Contiene la lógica de negocio pura, sin dependencias de frameworks.
 */
public class VenueUseCaseImpl implements
        CrearVenueUseCase,
        ActualizarVenueUseCase,
        EliminarVenueUseCase,
        ObtenerVenueUseCase,
        ListarVenuesUseCase {

    private final VenueRepositoryPort venueRepository;

    public VenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue ejecutar(Venue venue) {
        return venueRepository.guardar(venue);
    }

    @Override
    public Venue ejecutar(Long id, Venue venue) {
        Optional<Venue> existente = venueRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new NotFoundException("Venue con ID " + id + " no encontrado");
        }

        Venue venueExistente = existente.get();
        venueExistente.setName(venue.getName());
        venueExistente.setLocation(venue.getLocation());
        venueExistente.setCapacity(venue.getCapacity());

        return venueRepository.guardar(venueExistente);
    }

    @Override
    public void eliminar(Long id) {
        if (!venueRepository.existePorId(id)) {
            throw new NotFoundException("Venue con ID " + id + " no encontrado");
        }
        venueRepository.eliminar(id);
    }

    @Override
    public Optional<Venue> obtener(Long id) {
        return venueRepository.buscarPorId(id);
    }

    @Override
    public List<Venue> ejecutar() {
        return venueRepository.buscarTodos();
    }
}
