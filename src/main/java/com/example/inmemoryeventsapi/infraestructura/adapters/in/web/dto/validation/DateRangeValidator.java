package com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.validation;

import com.example.inmemoryeventsapi.infraestructura.adapters.in.web.dto.EventDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<ValidDateRange, EventDTO> {

    @Override
    public boolean isValid(EventDTO dto, ConstraintValidatorContext context) {
        if (dto.getDate() == null || dto.getEndDate() == null) {
            return true; // Let @NotNull handle nulls
        }

        try {
            LocalDate start = LocalDate.parse(dto.getDate());
            LocalDate end = LocalDate.parse(dto.getEndDate());
            return end.isAfter(start);
        } catch (Exception e) {
            return false; // Invalid date format should be handled by other validations
        }
    }
}
