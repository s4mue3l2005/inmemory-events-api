package com.example.inmemoryeventsapi.service;

import com.example.inmemoryeventsapi.dto.VenueDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VenueService {
    private final List<VenueDTO> venues = new ArrayList<>();
    private long idCounter = 1;

    public List<VenueDTO> findAll() {
        return venues;
    }

    public Optional<VenueDTO> findById(Long id) {
        return venues.stream().filter(v -> v.getId().equals(id)).findFirst();
    }

    public VenueDTO save(VenueDTO venue) {
        venue.setId(idCounter++);
        venues.add(venue);
        return venue;
    }

    public Optional<VenueDTO> update(Long id, VenueDTO updated) {
        return findById(id).map(v -> {
            v.setName(updated.getName());
            v.setLocation(updated.getLocation());
            v.setCapacity(updated.getCapacity());
            return v;
        });
    }

    public boolean delete(Long id) {
        return venues.removeIf(v -> v.getId().equals(id));
    }
}