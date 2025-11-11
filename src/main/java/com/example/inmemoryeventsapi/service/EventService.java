package com.example.inmemoryeventsapi.service;

import com.example.inmemoryeventsapi.dto.EventDTO;
import com.example.inmemoryeventsapi.entity.Event;
import com.example.inmemoryeventsapi.entity.Venue;
import com.example.inmemoryeventsapi.exception.BadRequestException;
import com.example.inmemoryeventsapi.exception.ConflictException;
import com.example.inmemoryeventsapi.exception.NotFoundException;
import com.example.inmemoryeventsapi.repository.EventRepository;
import com.example.inmemoryeventsapi.repository.VenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventService {

    private final EventRepository eventRepository;
    private final VenueRepository venueRepository;

    public EventService(EventRepository eventRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    public List<EventDTO> findAll() {
        return eventRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Page<EventDTO> findAll(Pageable pageable) {
        Page<Event> events = eventRepository.findAll(pageable);
        List<EventDTO> dtos = events.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, events.getTotalElements());
    }

    public Page<EventDTO> findByFilters(String city, String category, String fechaInicio, Pageable pageable) {
        LocalDate fechaInicioDate = null;
        if (fechaInicio != null && !fechaInicio.isBlank()) {
            try {
                fechaInicioDate = LocalDate.parse(fechaInicio);
            } catch (Exception e) {
                throw new BadRequestException("Formato de fecha inválido. Use formato: yyyy-MM-dd");
            }
        }

        Page<Event> events = eventRepository.findByFilters(
                city != null && !city.isBlank() ? city : null,
                category != null && !category.isBlank() ? category : null,
                fechaInicioDate,
                pageable
        );

        List<EventDTO> dtos = events.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtos, pageable, events.getTotalElements());
    }

    public Optional<EventDTO> findById(Long id) {
        return eventRepository.findById(id)
                .map(this::toDTO);
    }

    public List<EventDTO> findByVenueId(Long venueId) {
        return eventRepository.findAll().stream()
                .filter(e -> e.getVenue() != null && e.getVenue().getId().equals(venueId))
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EventDTO save(EventDTO eventDTO) {
        validateEvent(eventDTO);
        
        // Validar que el nombre no esté duplicado
        if (eventRepository.existsByName(eventDTO.getName())) {
            throw new ConflictException("Ya existe un evento con el nombre: " + eventDTO.getName());
        }

        Event event = toEntity(eventDTO);
        Event saved = eventRepository.save(event);
        return toDTO(saved);
    }

    public Optional<EventDTO> update(Long id, EventDTO eventDTO) {
        validateEvent(eventDTO);
        
        return eventRepository.findById(id)
                .map(existing -> {
                    // Validar duplicado solo si el nombre cambió
                    if (!existing.getName().equals(eventDTO.getName()) && 
                        eventRepository.existsByName(eventDTO.getName())) {
                        throw new ConflictException("Ya existe un evento con el nombre: " + eventDTO.getName());
                    }
                    
                    existing.setName(eventDTO.getName());
                    existing.setDate(parseDate(eventDTO.getDate()));
                    existing.setCapacity(eventDTO.getCapacity());
                    existing.setPrice(eventDTO.getPrice());
                    existing.setCategory(eventDTO.getCategory());
                    existing.setCity(eventDTO.getCity());
                    
                    if (eventDTO.getVenueId() != null) {
                        Venue venue = venueRepository.findById(eventDTO.getVenueId())
                                .orElseThrow(() -> new BadRequestException("El lugar del evento no existe"));
                        existing.setVenue(venue);
                    } else {
                        existing.setVenue(null);
                    }
                    
                    Event updated = eventRepository.save(existing);
                    return toDTO(updated);
                });
    }

    public boolean delete(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new NotFoundException("Evento con ID " + id + " no encontrado");
        }
        eventRepository.deleteById(id);
        return true;
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
            venueRepository.findById(event.getVenueId())
                    .orElseThrow(() -> new BadRequestException("El lugar del evento no existe"));
        }
        if (event.getCapacity() != null && event.getCapacity() < 0) {
            throw new BadRequestException("La capacidad del evento no puede ser negativa");
        }
        if (event.getPrice() != null && event.getPrice() < 0) {
            throw new BadRequestException("El precio del evento no puede ser negativo");
        }
    }

    private Event toEntity(EventDTO dto) {
        Event event = new Event();
        event.setName(dto.getName());
        event.setDate(parseDate(dto.getDate()));
        event.setCapacity(dto.getCapacity());
        event.setPrice(dto.getPrice());
        event.setCategory(dto.getCategory());
        event.setCity(dto.getCity());
        
        if (dto.getVenueId() != null) {
            Venue venue = venueRepository.findById(dto.getVenueId())
                    .orElseThrow(() -> new BadRequestException("El lugar del evento no existe"));
            event.setVenue(venue);
        }
        
        return event;
    }

    private EventDTO toDTO(Event entity) {
        EventDTO dto = new EventDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDate(entity.getDate() != null ? entity.getDate().toString() : null);
        dto.setCapacity(entity.getCapacity());
        dto.setPrice(entity.getPrice());
        dto.setCategory(entity.getCategory());
        dto.setCity(entity.getCity());
        dto.setVenueId(entity.getVenue() != null ? entity.getVenue().getId() : null);
        return dto;
    }

    private LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isBlank()) {
            return null;
        }
        try {
            LocalDate date = LocalDate.parse(dateStr);
            // Validar que la fecha sea futura
            if (date.isBefore(LocalDate.now())) {
                throw new BadRequestException("La fecha del evento debe ser futura");
            }
            return date;
        } catch (BadRequestException e) {
            throw e;
        } catch (Exception e) {
            throw new BadRequestException("Formato de fecha inválido. Use formato: yyyy-MM-dd");
        }
    }
}
