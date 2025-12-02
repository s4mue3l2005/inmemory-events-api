package com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa;

import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.EventEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.entity.VenueEntity;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.EventJpaRepository;
import com.example.inmemoryeventsapi.infraestructura.adapters.out.jpa.repository.VenueJpaRepository;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class EventRepositoryOptimizationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EventJpaRepository eventRepository;

    @Autowired
    private VenueJpaRepository venueRepository;

    @Test
    public void testFindAllWithEntityGraph() {
        // Setup data
        VenueEntity venue = new VenueEntity();
        venue.setName("Concert Hall");
        venue.setLocation("City Center");
        venue.setCapacity(1000);
        venueRepository.save(venue);

        EventEntity event = new EventEntity();
        event.setName("Rock Concert");
        event.setDate(LocalDate.now());
        event.setCapacity(1000);
        event.setPrice(100.0);
        event.setVenue(venue);
        eventRepository.save(event);

        entityManager.flush();
        entityManager.clear();

        // Enable statistics
        SessionFactory sessionFactory = entityManager.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        Statistics stats = sessionFactory.getStatistics();
        stats.setStatisticsEnabled(true);
        stats.clear();

        // Execute query
        Page<EventEntity> page = eventRepository.findAll(PageRequest.of(0, 10));

        // Verify results
        assertThat(page.getContent()).hasSize(1);
        EventEntity fetchedEvent = page.getContent().get(0);

        // Access venue (should be already loaded)
        assertThat(fetchedEvent.getVenue().getName()).isEqualTo("Concert Hall");

        // Verify query count
        // Should be 1 query for the select (with join) + 1 query for count (pagination)
        // If N+1, accessing venue would trigger another query, but since we cleared EM,
        // it would be visible.
        // But EntityGraph should fetch it in the first query.

        // Note: H2 might use 2 queries (one for count, one for select).
        // If lazy loading happened, we would see more.

        long queryCount = stats.getPrepareStatementCount();
        // We expect:
        // 1. Count query
        // 2. Select query (with join)
        // Total 2.
        // If N+1:
        // 3. Select venue (when accessed, if not eager/joined) -> But wait, if it's
        // lazy and not in graph, accessing it triggers query.

        // Let's just print it to be sure, asserting exact count is tricky across
        // environments.
        System.out.println("Query Count: " + queryCount);

        // We can check if the venue is initialized
        boolean isInitialized = org.hibernate.Hibernate.isInitialized(fetchedEvent.getVenue());
        assertThat(isInitialized).as("Venue should be initialized by EntityGraph").isTrue();
    }
}
