package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa;

import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;
import com.example.inmemoryeventsapi.dominio.ports.out.EventoRepositoryPort;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper.EventoMapper;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper.PageMapper;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.EventJpaRepository;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Adaptador JPA que implementa el puerto de repositorio de eventos.
 * Traduce entre el dominio y la capa de persistencia JPA.
 */
@Component
public class EventoJpaAdapter implements EventoRepositoryPort {

    private final EventJpaRepository eventJpaRepository;
    private final VenueJpaRepository venueJpaRepository;
    private final EventoMapper eventoMapper;

    public EventoJpaAdapter(EventJpaRepository eventJpaRepository,
            VenueJpaRepository venueJpaRepository,
            EventoMapper eventoMapper) {
        this.eventJpaRepository = eventJpaRepository;
        this.venueJpaRepository = venueJpaRepository;
        this.eventoMapper = eventoMapper;
    }

    @Override
    public Event guardar(Event event) {
        EventEntity entity = eventoMapper.toEntity(event);

        // Si tiene venueId, cargar la entidad venue
        if (event.getVenueId() != null) {
            VenueEntity venue = venueJpaRepository.findById(event.getVenueId())
                    .orElse(null);
            entity.setVenue(venue);
        }

        EventEntity saved = eventJpaRepository.save(entity);
        return eventoMapper.toDomain(saved);
    }

    @Override
    public Optional<Event> buscarPorId(Long id) {
        return eventJpaRepository.findById(id)
                .map(eventoMapper::toDomain);
    }

    @Override
    public Page<Event> buscarTodos(Pageable pageable) {
        org.springframework.data.domain.Pageable springPageable = PageMapper.toSpring(pageable);
        org.springframework.data.domain.Page<EventEntity> entities = eventJpaRepository.findAll(springPageable);
        return PageMapper.toDomain(entities.map(eventoMapper::toDomain), pageable);
    }

    @Override
    public Page<Event> buscarConFiltros(String city, String category, LocalDate fechaInicio, Pageable pageable) {
        org.springframework.data.domain.Pageable springPageable = PageMapper.toSpring(pageable);
        org.springframework.data.domain.Page<EventEntity> entities = eventJpaRepository.findByFilters(city, category, fechaInicio, springPageable);
        return PageMapper.toDomain(entities.map(eventoMapper::toDomain), pageable);
    }

    @Override
    public boolean existePorNombre(String name) {
        return eventJpaRepository.existsByName(name);
    }

    @Override
    public boolean existePorId(Long id) {
        return eventJpaRepository.existsById(id);
    }

    @Override
    public void eliminar(Long id) {
        eventJpaRepository.deleteById(id);
    }
}
