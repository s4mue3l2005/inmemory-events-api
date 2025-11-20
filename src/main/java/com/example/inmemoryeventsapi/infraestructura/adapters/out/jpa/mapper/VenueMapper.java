package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper;

import com.example.inmemoryeventsapi.dominio.model.Venue;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import org.mapstruct.Mapper;

/**
 * Mapper de MapStruct para convertir entre VenueEntity (JPA) y Venue (dominio).
 */
@Mapper(componentModel = "spring")
public interface VenueMapper {

    Venue toDomain(VenueEntity entity);

    VenueEntity toEntity(Venue domain);
}
