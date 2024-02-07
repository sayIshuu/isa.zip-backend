package backend.zip.service.schedule;

import backend.zip.Repository.schedule.EventRepository;
import backend.zip.repository.UserRepository;
import backend.zip.Repository.schedule.ScheduleRepository;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.dto.schedule.request.UpdateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.core.userdetails.UserDetailsResourceFactoryBean;
import org.springframework.stereotype.Service;
import backend.zip.domain.schedule.Event;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public void addEvent(Schedule schedule, LocalDate eventDate, String eventTitle)
    {
        Event event = Event.toEntity(schedule,eventDate,eventTitle);
        eventRepository.save(event);
    }

    @Override
    public List<Event> getEvents(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null)
        {
            Optional<Schedule> scheduleOptional = scheduleRepository.findByUserId(userId);
            if (scheduleOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                return eventRepository.findBySchedule(schedule);
            }
        }

    //TODO error  처리
        return Collections.emptyList();
    }

    @Override
    public Optional<Event> updateEvent(UpdateEventRequest request) {

        //들어오는 값을 어떻게 설정해야할지 의문
        Optional<Event> eventOptional = eventRepository.findById(request.getEventId());

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            event.setEventDate(request.getEventDate());
            event.setEventTitle(request.getEventTitle());
            eventRepository.save(event);
            return Optional.of(event);
        }

    //TODO 에러처리
        return Optional.empty();
    }

    @Override
    public void deleteEvent(Long eventId){
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent())
        {
            Event event = eventOptional.get();
            eventRepository.deleteById(eventId);
        }
    }
}
