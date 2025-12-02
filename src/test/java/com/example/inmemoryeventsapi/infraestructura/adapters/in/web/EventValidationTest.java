package com.example.inmemoryeventsapi.infraestructura.adapters.in.web;

import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.EventDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EventValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenEndDateIsAfterStartDate_thenValidationSucceeds() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Valid Event");
        eventDTO.setDate("2023-10-01");
        eventDTO.setEndDate("2023-10-02");

        Set<jakarta.validation.ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenEndDateIsBeforeStartDate_thenValidationFails() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Invalid Event");
        eventDTO.setDate("2023-10-05");
        eventDTO.setEndDate("2023-10-01");

        Set<jakarta.validation.ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream()
                .anyMatch(v -> v.getMessage().contains("La fecha de fin debe ser posterior a la fecha de inicio")));
    }

    @Test
    void whenEndDateIsNull_thenValidationSucceeds() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Event without End Date");
        eventDTO.setDate("2023-10-01");
        eventDTO.setEndDate(null);

        Set<jakarta.validation.ConstraintViolation<EventDTO>> violations = validator.validate(eventDTO);
        assertTrue(violations.isEmpty());
    }
}
