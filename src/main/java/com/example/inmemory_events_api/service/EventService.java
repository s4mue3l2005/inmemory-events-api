package com.example.inmemory_events_api.service;

import com.example.inmemoryeventsapi.dto.EventDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final List<EventDTO> events = new ArrayList<>();
    private long idCounter = 1;

    public List<EventDTO> findAll() {
        return events;
    }

    public Optional<EventDTO> findById(Long id) {
        return events.stream().filter(e -> e.getId().equals(id)).findFirst();
    }

    public EventDTO save(EventDTO event) {
        event.setId(idCounter++);
        events.add(event);
        return event;
    }

    public Optional<EventDTO> update(Long id, EventDTO updated) {
        return findById(id).map(e -> {
            e.setName(updated.getName());
            e.setDate(updated.getDate());
            e.setVenue(updated.getVenue());
            return e;
        });
    }

    public boolean delete(Long id) {
        return events.removeIf(e -> e.getId().equals(id));
    }
}
