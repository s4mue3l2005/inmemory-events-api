package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper;

import com.example.inmemoryeventsapi.dominio.model.Venue;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.VenueDTO;
import org.mapstruct.Mapper;

/**
 * Mapper de MapStruct para convertir entre VenueDTO y Venue (dominio).
 */
@Mapper(componentModel = "spring")
public interface VenueDTOMapper {

    VenueDTO toDTO(Venue domain);

    Venue toDomain(VenueDTO dto);
}
