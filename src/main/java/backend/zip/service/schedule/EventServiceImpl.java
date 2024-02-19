package backend.zip.service.schedule;

import backend.zip.global.exception.schedule.DateNotFoundException;
import backend.zip.global.exception.schedule.EventNotFoundException;
import backend.zip.global.exception.schedule.ScheduleNotFoundException;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.repository.schedule.EventRepository;
import backend.zip.repository.user.UserRepository;
import backend.zip.repository.schedule.ScheduleRepository;
import backend.zip.domain.enums.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.dto.schedule.request.UpdateEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import backend.zip.domain.enums.schedule.Event;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static backend.zip.global.status.ErrorStatus.*;

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
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));
        if (user != null)
        {
            Optional<Schedule> scheduleOptional = scheduleRepository.findByUserId(userId);
            if (scheduleOptional.isPresent()) {
                Schedule schedule = scheduleOptional.get();
                return eventRepository.findBySchedule(schedule);
            }
            else
                throw new ScheduleNotFoundException(SCHEDULE_NOT_FOUND);
        }
        return Collections.emptyList();
    }

    @Override
    public Optional<Event> updateEvent(UpdateEventRequest request) {
        Optional<Event> eventOptional = eventRepository.findById(request.getEventId());

        if (eventOptional.isPresent()) {
            Event event = eventOptional.get();
            // 엔티티의 변경 사항 설정
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
                LocalDate eventDate = LocalDate.parse(request.getEventDate(), formatter);
                event.setEventDate(eventDate);
            } catch (DateTimeParseException ex) {
                throw new DateNotFoundException(DATE_NOT_FOUND);
            }
            event.setEventTitle(request.getEventTitle());
            // 변경된 엔티티 저장
            event = eventRepository.save(event);
            return Optional.of(event);
        } else {
            // 이벤트를 찾을 수 없음 예외 던지기
            throw new EventNotFoundException(EVENT_NOT_FOUND);
        }
    }


    @Override
    public void deleteEvent(Long eventId){
        Optional<Event> eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isPresent())
        {
            Event event = eventOptional.get();
            eventRepository.deleteById(eventId);
        }
        else
            throw new EventNotFoundException(EVENT_NOT_FOUND);
    }

    @Override
    public void deleteEvents(Long scheduleId) {
        Optional<Schedule> scheduleOptional = scheduleRepository.findById(scheduleId);
        if (scheduleOptional.isPresent()) {
            Schedule schedule = scheduleOptional.get();
            List<Event> events = eventRepository.findBySchedule(schedule);
            for (Event event : events) {
                eventRepository.delete(event);
            }

        } else {
            throw new ScheduleNotFoundException(SCHEDULE_NOT_FOUND);
        }
    }
}
