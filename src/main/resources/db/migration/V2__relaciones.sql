ALTER TABLE events ADD CONSTRAINT uk_event_name UNIQUE (name);

ALTER TABLE events ADD CONSTRAINT fk_event_venue FOREIGN KEY (venue_id) REFERENCES venues (id);
