package backend.zip.service.schedule;

import backend.zip.domain.schedule.Event;
import backend.zip.Repository.schedule.EventRepository;
import backend.zip.domain.schedule.Schedule;
import backend.zip.dto.schedule.request.UpdateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public interface EventService {
    void addEvent(Schedule schedule, LocalDate eventDate, String eventTitle);
    List<Event> getEvents(Long userId);
    Optional<Event> updateEvent(UpdateEventRequest request);
    void deleteEvent(Long eventId);
}

