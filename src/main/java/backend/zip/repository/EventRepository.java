package backend.zip.Repository;

import backend.zip.domain.schedule.Event;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository {
    Optional<Event> findAll();

    Optional<Event> findById(Long eventId);

    Event save(Event event);
}
