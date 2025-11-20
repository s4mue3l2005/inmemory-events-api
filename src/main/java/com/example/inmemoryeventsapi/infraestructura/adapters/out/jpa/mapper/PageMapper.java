package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.mapper;

import com.example.inmemoryeventsapi.dominio.model.Page;
import com.example.inmemoryeventsapi.dominio.model.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Mapper para convertir entre Page/Pageable de Spring y las clases de dominio.
 */
public class PageMapper {

    /**
     * Convierte un Page de Spring a un Page del dominio.
     */
    public static <T> Page<T> toDomain(org.springframework.data.domain.Page<T> springPage, Pageable domainPageable) {
        List<T> content = springPage.getContent();
        return new Page<>(
                content,
                domainPageable.getPageNumber(),
                domainPageable.getPageSize(),
                springPage.getTotalElements()
        );
    }

    /**
     * Convierte un Pageable del dominio a un Pageable de Spring.
     */
    public static org.springframework.data.domain.Pageable toSpring(Pageable domainPageable) {
        if (domainPageable.getSortBy() != null && !domainPageable.getSortBy().isEmpty()) {
            Sort.Direction direction = "desc".equalsIgnoreCase(domainPageable.getSortDirection())
                    ? Sort.Direction.DESC
                    : Sort.Direction.ASC;
            Sort sort = Sort.by(direction, domainPageable.getSortBy());
            return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize(), sort);
        }
        return PageRequest.of(domainPageable.getPageNumber(), domainPageable.getPageSize());
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

