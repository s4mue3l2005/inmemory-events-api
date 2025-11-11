package com.example.inmemoryeventsapi.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventDTO {
    private Long id;

    @NotBlank(message = "El nombre del evento es obligatorio")
    @Size(min = 3, max = 100, message = "El nombre debe tener entre 3 y 100 caracteres")
    private String name;

    @NotBlank(message = "La fecha del evento es obligatoria")
    private String date;

    private Long venueId;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    private Integer capacity;

    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private Double price;

    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String category;

    @Size(max = 100, message = "La ciudad no puede exceder 100 caracteres")
    private String city;
}