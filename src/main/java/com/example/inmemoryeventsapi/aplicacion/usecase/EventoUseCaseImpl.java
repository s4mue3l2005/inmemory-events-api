package com.example.inmemoryeventsapi.aplicacion.usecase;

import com.example.inmemoryeventsapi.dominio.exception.BadRequestException;
import com.example.inmemoryeventsapi.dominio.exception.ConflictException;
import com.example.inmemoryeventsapi.dominio.exception.NotFoundException;
import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;
import com.example.inmemoryeventsapi.dominio.ports.in.*;
import com.example.inmemoryeventsapi.dominio.ports.out.EventoRepositoryPort;
import com.example.inmemoryeventsapi.dominio.ports.out.VenueRepositoryPort;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Implementación de los casos de uso de eventos.
 * Contiene la lógica de negocio pura, sin dependencias de frameworks.
 */
@Transactional(readOnly = true)
public class EventoUseCaseImpl implements
        CrearEventoUseCase,
        ActualizarEventoUseCase,
        EliminarEventoUseCase,
        ObtenerEventoUseCase,
        ListarEventosUseCase {

    private final EventoRepositoryPort eventoRepository;
    private final VenueRepositoryPort venueRepository;

    public EventoUseCaseImpl(EventoRepositoryPort eventoRepository, VenueRepositoryPort venueRepository) {
        this.eventoRepository = eventoRepository;
        this.venueRepository = venueRepository;
    }

    // CrearEventoUseCase
    @Override
    @Transactional
    public Event ejecutar(Event event) {
        validarEvento(event);

        // Validar que el nombre no esté duplicado
        if (eventoRepository.existePorNombre(event.getName())) {
            throw new ConflictException("Ya existe un evento con el nombre: " + event.getName());
        }

        return eventoRepository.guardar(event);
    }

    // ActualizarEventoUseCase
    @Override
    @Transactional
    public Event ejecutar(Long id, Event event) {
        validarEvento(event);

        Optional<Event> existente = eventoRepository.buscarPorId(id);
        if (existente.isEmpty()) {
            throw new NotFoundException("Evento con ID " + id + " no encontrado");
        }

        Event eventoExistente = existente.get();

        // Validar duplicado solo si el nombre cambió
        if (!eventoExistente.getName().equals(event.getName()) &&
                eventoRepository.existePorNombre(event.getName())) {
            throw new ConflictException("Ya existe un evento con el nombre: " + event.getName());
        }

        // Actualizar campos
        eventoExistente.setName(event.getName());
        eventoExistente.setDate(event.getDate());
        eventoExistente.setCapacity(event.getCapacity());
        eventoExistente.setPrice(event.getPrice());
        eventoExistente.setCategory(event.getCategory());
        eventoExistente.setCity(event.getCity());
        eventoExistente.setVenueId(event.getVenueId());

        return eventoRepository.guardar(eventoExistente);
    }

    // EliminarEventoUseCase
    @Override
    @Transactional
    public void eliminar(Long id) {
        if (!eventoRepository.existePorId(id)) {
            throw new NotFoundException("Evento con ID " + id + " no encontrado");
        }
        eventoRepository.eliminar(id);
    }

    // ObtenerEventoUseCase
    @Override
    public Optional<Event> obtener(Long id) {
        return eventoRepository.buscarPorId(id);
    }

    // ListarEventosUseCase
    @Override
    public Page<Event> ejecutar(Pageable pageable) {
        return eventoRepository.buscarTodos(pageable);
    }

    @Override
    public Page<Event> ejecutarConFiltros(String city, String category, String fechaInicio, Pageable pageable) {
        LocalDate fechaInicioDate = null;
        if (fechaInicio != null && !fechaInicio.isBlank()) {
            try {
                fechaInicioDate = LocalDate.parse(fechaInicio);
            } catch (Exception e) {
                throw new BadRequestException("Formato de fecha inválido. Use formato: yyyy-MM-dd");
            }
        }

        return eventoRepository.buscarConFiltros(
                city != null && !city.isBlank() ? city : null,
                category != null && !category.isBlank() ? category : null,
                fechaInicioDate,
                pageable);
    }

    private void validarEvento(Event event) {
        if (event == null) {
            throw new BadRequestException("El evento no puede ser nulo");
        }
        if (event.getName() == null || event.getName().isBlank()) {
            throw new BadRequestException("El nombre del evento no puede estar vacío");
        }
        if (event.getDate() == null) {
            throw new BadRequestException("La fecha del evento es requerida");
        }
        // Validar que la fecha sea futura
        if (event.getDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("La fecha del evento debe ser futura");
        }
        if (event.getVenueId() != null) {
            if (!venueRepository.existePorId(event.getVenueId())) {
                throw new BadRequestException("El lugar del evento no existe");
            }
        }
        if (event.getCapacity() != null && event.getCapacity() < 0) {
            throw new BadRequestException("La capacidad del evento no puede ser negativa");
        }
        if (event.getPrice() != null && event.getPrice() < 0) {
            throw new BadRequestException("El precio del evento no puede ser negativo");
        }
    }
}
