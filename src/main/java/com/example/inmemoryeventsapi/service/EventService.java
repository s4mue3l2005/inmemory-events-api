package com.example.inmemoryeventsapi.service;

import com.example.inmemoryeventsapi.dto.EventDTO;
import com.example.inmemoryeventsapi.dto.VenueDTO;
import com.example.inmemoryeventsapi.exception.BadRequestException;
import com.example.inmemoryeventsapi.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    private final List<EventDTO> events = new ArrayList<>();
    private final VenueService venueService;

    public EventService(VenueService venueService) {
        this.venueService = venueService;
    }

    public List<EventDTO> findAll() {
        return new ArrayList<>(events);
    }

    public Optional<EventDTO> findById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    public List<EventDTO> findByVenueId(Long venueId) {
        return events.stream()
                .filter(e -> e.getVenueId() != null && e.getVenueId().equals(venueId))
                .collect(Collectors.toList());
    }

    public EventDTO save(EventDTO event) {
        validateEvent(event);
        event.setId((long) (events.size() + 1));
        events.add(event);
        return event;
    }

    public Optional<EventDTO> update(Long id, EventDTO event) {
        validateEvent(event);
        return findById(id).map(existing -> {
            existing.setName(event.getName());
            existing.setDate(event.getDate());
            existing.setVenueId(event.getVenueId());
            existing.setCapacity(event.getCapacity());
            existing.setPrice(event.getPrice());
            return existing;
        }).or(() -> {
            throw new NotFoundException("Evento con ID " + id + " no encontrado");
        });
    }

    public boolean delete(Long id) {
        return findById(id)
                .map(events::remove)
                .orElseThrow(() -> new NotFoundException("Evento con ID " + id + " no encontrado"));
    }

    private void validateEvent(EventDTO event) {
        if (event == null) {
            throw new BadRequestException("El evento no puede ser nulo");
        }
        if (event.getName() == null || event.getName().isBlank()) {
            throw new BadRequestException("El nombre del evento no puede estar vacío");
        }
        if (event.getDate() == null) {
            throw new BadRequestException("La fecha del evento es requerida");
        }
        if (event.getVenueId() != null) {
            venueService.findById(event.getVenueId())
                    .orElseThrow(() -> new BadRequestException("El lugar del evento no existe"));
        }
        if (event.getCapacity() != null && event.getCapacity() < 0) {
            throw new BadRequestException("La capacidad del evento no puede ser negativa");
        }
        if (event.getPrice() != null && event.getPrice() < 0) {
            throw new BadRequestException("El precio del evento no puede ser negativo");
        }
    }
}
