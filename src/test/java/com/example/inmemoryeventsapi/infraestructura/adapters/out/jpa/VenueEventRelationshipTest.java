package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.EventJpaRepository;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VenueEventRelationshipTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private VenueJpaRepository venueRepository;

    @Autowired
    private EventJpaRepository eventRepository;

    @Test
    public void testCascadePersistAndOrphanRemoval() {
        // Create Venue
        VenueEntity venue = new VenueEntity();
        venue.setName("Grand Hall");
        venue.setLocation("New York");
        venue.setCapacity(500);

        // Create Event
        EventEntity event = new EventEntity();
        event.setName("Gala Night");
        event.setDate(LocalDate.now().plusDays(10));
        event.setCapacity(100);
        event.setPrice(50.0);
        event.setVenue(venue); // Set owning side

        // Add to list (inverse side)
        venue.getEvents().add(event);

        // Save Venue (should cascade to Event)
        VenueEntity savedVenue = venueRepository.save(venue);
        entityManager.flush();
        entityManager.clear();

        // Verify Venue is saved
        assertThat(savedVenue.getId()).isNotNull();

        // Verify Event is saved via cascade
        List<EventEntity> events = eventRepository.findAll();
        assertThat(events).hasSize(1);
        assertThat(events.get(0).getVenue().getId()).isEqualTo(savedVenue.getId());

        // Test Orphan Removal (Delete Venue should delete Event)
        venueRepository.deleteById(savedVenue.getId());
        entityManager.flush();
        entityManager.clear();

        assertThat(venueRepository.findById(savedVenue.getId())).isEmpty();
        assertThat(eventRepository.findAll()).isEmpty();
    }
}
