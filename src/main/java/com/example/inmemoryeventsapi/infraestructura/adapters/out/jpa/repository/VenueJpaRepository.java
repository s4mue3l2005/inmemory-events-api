package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio JPA para VenueEntity.
 * 
 * Spring Data JPA proporciona automáticamente los siguientes métodos al extender JpaRepository:
 * - save(VenueEntity) - Guardar o actualizar
 * - findById(Long) - Buscar por ID
 * - findAll() - Listar todos
 * - existsById(Long) - Verificar existencia por ID
 * - deleteById(Long) - Eliminar por ID
 * 
 * Este repositorio está siendo utilizado en:
 * - VenueJpaAdapter: Para todas las operaciones CRUD de venues
 * - EventoJpaAdapter: Para cargar la entidad Venue al guardar un Event
 */
public interface VenueJpaRepository extends JpaRepository<VenueEntity, Long> {
    // No se requieren métodos personalizados.
    // Todos los métodos necesarios son proporcionados automáticamente por Spring Data JPA.
}
