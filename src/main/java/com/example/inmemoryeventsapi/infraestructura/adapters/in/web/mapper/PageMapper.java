package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.mapper;

import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * Mapper para convertir entre Page/Pageable de Spring y las clases de dominio en la capa web.
 */
public class PageMapper {

    /**
     * Convierte un Page del dominio a un Page de Spring para la respuesta HTTP.
     */
    public static <T> org.springframework.data.domain.Page<T> toSpring(Page<T> domainPage) {
        return new PageImpl<>(
                domainPage.getContent(),
                PageRequest.of(domainPage.getPageNumber(), domainPage.getPageSize()),
                domainPage.getTotalElements()
        );
    }

    /**
     * Convierte un Pageable de Spring a un Pageable del dominio.
     */
    public static Pageable toDomain(org.springframework.data.domain.Pageable springPageable) {
        Pageable domainPageable = new Pageable(
                springPageable.getPageNumber(),
                springPageable.getPageSize()
        );

        if (springPageable.getSort().isSorted()) {
            Sort.Order order = springPageable.getSort().iterator().next();
            domainPageable.setSortBy(order.getProperty());
            domainPageable.setSortDirection(order.getDirection().name().toLowerCase());
        }

        return domainPageable;
    }
}

