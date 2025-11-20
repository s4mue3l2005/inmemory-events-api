package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa;

import com.example.inmemoryeventsapi.dominio.model.Venue;
import com.example.inmemoryeventsapi.dominio.ports.out.VenueRepositoryPort;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper.VenueMapper;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador JPA que implementa el puerto de repositorio de venues.
 * Traduce entre el dominio y la capa de persistencia JPA.
 */
@Component
public class VenueJpaAdapter implements VenueRepositoryPort {

    private final VenueJpaRepository venueJpaRepository;
    private final VenueMapper venueMapper;

    public VenueJpaAdapter(VenueJpaRepository venueJpaRepository, VenueMapper venueMapper) {
        this.venueJpaRepository = venueJpaRepository;
        this.venueMapper = venueMapper;
    }

    @Override
    public Venue guardar(Venue venue) {
        VenueEntity entity = venueMapper.toEntity(venue);
        VenueEntity saved = venueJpaRepository.save(entity);
        return venueMapper.toDomain(saved);
    }

    @Override
    public Optional<Venue> buscarPorId(Long id) {
        return venueJpaRepository.findById(id)
                .map(venueMapper::toDomain);
    }

    @Override
    public List<Venue> buscarTodos() {
        return venueJpaRepository.findAll().stream()
                .map(venueMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean existePorId(Long id) {
        return venueJpaRepository.existsById(id);
    }

    @Override
    public void eliminar(Long id) {
        venueJpaRepository.deleteById(id);
    }
}
