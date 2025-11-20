package com.example.inmemoryeventsapi.service;

import com.example.inmemoryeventsapi.dto.VenueDTO;
import com.example.inmemoryeventsapi.entity.Venue;
import com.example.inmemoryeventsapi.exception.NotFoundException;
import com.example.inmemoryeventsapi.repository.VenueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class VenueService {
    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueDTO> findAll() {
        return venueRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Optional<VenueDTO> findById(Long id) {
        return venueRepository.findById(id)
                .map(this::toDTO);
    }

    public VenueDTO save(VenueDTO venueDTO) {
        Venue venue = toEntity(venueDTO);
        Venue saved = venueRepository.save(venue);
        return toDTO(saved);
    }

    public Optional<VenueDTO> update(Long id, VenueDTO venueDTO) {
        return venueRepository.findById(id)
                .map(existing -> {
                    existing.setName(venueDTO.getName());
                    existing.setLocation(venueDTO.getLocation());
                    existing.setCapacity(venueDTO.getCapacity());
                    Venue updated = venueRepository.save(existing);
                    return toDTO(updated);
                });
    }

    public boolean delete(Long id) {
        if (!venueRepository.existsById(id)) {
            throw new NotFoundException("Venue con ID " + id + " no encontrado");
        }
        venueRepository.deleteById(id);
        return true;
    }

    private Venue toEntity(VenueDTO dto) {
        Venue venue = new Venue();
        venue.setName(dto.getName());
        venue.setLocation(dto.getLocation());
        venue.setCapacity(dto.getCapacity());
        return venue;
    }

    private VenueDTO toDTO(Venue entity) {
        VenueDTO dto = new VenueDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setCapacity(entity.getCapacity());
        return dto;
    }
}