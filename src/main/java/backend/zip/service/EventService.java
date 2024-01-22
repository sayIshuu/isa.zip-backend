package backend.zip.service;

import backend.zip.Repository.EventRepository;
import backend.zip.domain.schedule.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;
    //전체 조회
    public Optional<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    //상세 일정 하나만 조회
    public Optional<Event> getEventById(Long eventId) { // 어떤 기준으로 뭘로 조회할건지?
        return eventRepository.findById(eventId);
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Optional<Event> updateEvent(Long eventId, Event updatedEvent) {
        return getEventById(eventId).map(existingEvent -> {
            existingEvent.setEventTitle(updatedEvent.getEventTitle());
            return eventRepository.save(existingEvent);
        });
    }

}

