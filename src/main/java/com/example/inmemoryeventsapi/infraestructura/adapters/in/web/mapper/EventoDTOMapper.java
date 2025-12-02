package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper;

import com.example.inmemoryeventsapi.dominio.model.Event;
import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.EventDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper de MapStruct para convertir entre EventDTO y Event (dominio).
 */
@Mapper(componentModel = "spring")
public interface EventoDTOMapper {

    @Mapping(target = "date", expression = "java(domain.getDate() != null ? domain.getDate().toString() : null)")
    @Mapping(target = "endDate", expression = "java(domain.getEndDate() != null ? domain.getEndDate().toString() : null)")
    EventDTO toDTO(Event domain);

    @Mapping(target = "date", expression = "java(dto.getDate() != null ? java.time.LocalDate.parse(dto.getDate()) : null)")
    @Mapping(target = "endDate", expression = "java(dto.getEndDate() != null ? java.time.LocalDate.parse(dto.getEndDate()) : null)")
    Event toDomain(EventDTO dto);
}
