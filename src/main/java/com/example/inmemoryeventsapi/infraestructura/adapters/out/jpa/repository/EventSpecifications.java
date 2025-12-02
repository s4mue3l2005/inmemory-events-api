package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventSpecifications {

    public static Specification<EventEntity> withFilters(String city, String category, LocalDate fechaInicio) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (city != null && !city.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("city"), city));
            }

            if (category != null && !category.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("category"), category));
            }

            if (fechaInicio != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("date"), fechaInicio));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
