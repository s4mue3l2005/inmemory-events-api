package com.example.inmemoryeventsapi.infraestructura.config;

import com.example.inmemoryeventsapi.aplicacion.usecase.EventoUseCaseImpl;
import com.example.inmemoryeventsapi.aplicacion.usecase.VenueUseCaseImpl;
import com.example.inmemoryeventsapi.dominio.ports.in.*;
import com.example.inmemoryeventsapi.dominio.ports.out.EventoRepositoryPort;
import com.example.inmemoryeventsapi.dominio.ports.out.VenueRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuración de beans para la arquitectura hexagonal.
 * Conecta los puertos con sus implementaciones (adaptadores).
 */
@Configuration
public class BeanConfiguration {

    /**
     * Bean que implementa todos los casos de uso de eventos.
     */
    @Bean
    public EventoUseCaseImpl eventoUseCase(EventoRepositoryPort eventoRepositoryPort,
            VenueRepositoryPort venueRepositoryPort) {
        return new EventoUseCaseImpl(eventoRepositoryPort, venueRepositoryPort);
    }

    /**
     * Bean que implementa todos los casos de uso de venues.
     */
    @Bean
    public VenueUseCaseImpl venueUseCase(VenueRepositoryPort venueRepositoryPort) {
        return new VenueUseCaseImpl(venueRepositoryPort);
    }

    // Beans individuales para inyección de dependencias específicas

    @Bean
    public CrearEventoUseCase crearEventoUseCase(EventoUseCaseImpl eventoUseCase) {
        return eventoUseCase;
    }

    @Bean
    public ActualizarEventoUseCase actualizarEventoUseCase(EventoUseCaseImpl eventoUseCase) {
        return eventoUseCase;
    }

    @Bean
    public EliminarEventoUseCase eliminarEventoUseCase(EventoUseCaseImpl eventoUseCase) {
        return eventoUseCase;
    }

    @Bean
    public ObtenerEventoUseCase obtenerEventoUseCase(EventoUseCaseImpl eventoUseCase) {
        return eventoUseCase;
    }

    @Bean
    public ListarEventosUseCase listarEventosUseCase(EventoUseCaseImpl eventoUseCase) {
        return eventoUseCase;
    }

    @Bean
    public CrearVenueUseCase crearVenueUseCase(VenueUseCaseImpl venueUseCase) {
        return venueUseCase;
    }

    @Bean
    public ActualizarVenueUseCase actualizarVenueUseCase(VenueUseCaseImpl venueUseCase) {
        return venueUseCase;
    }

    @Bean
    public EliminarVenueUseCase eliminarVenueUseCase(VenueUseCaseImpl venueUseCase) {
        return venueUseCase;
    }

    @Bean
    public ObtenerVenueUseCase obtenerVenueUseCase(VenueUseCaseImpl venueUseCase) {
        return venueUseCase;
    }

    @Bean
    public ListarVenuesUseCase listarVenuesUseCase(VenueUseCaseImpl venueUseCase) {
        return venueUseCase;
    }
}
