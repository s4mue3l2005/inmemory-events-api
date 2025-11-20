package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper;

import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper de MapStruct para convertir entre EventEntity (JPA) y Event (dominio).
 */
@Mapper(componentModel = "spring")
public interface EventoMapper {

    @Mapping(source = "venue.id", target = "venueId")
    Event toDomain(EventEntity entity);

    @Mapping(target = "venue", ignore = true)
    EventEntity toEntity(Event domain);
}
