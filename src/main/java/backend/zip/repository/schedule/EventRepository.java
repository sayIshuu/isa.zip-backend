package backend.zip.repository.schedule;

import backend.zip.domain.enums.schedule.Event;
import backend.zip.domain.enums.schedule.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    //schedule로 조회
    List<Event> findBySchedule(Schedule schedule);
    Event findFirstByScheduleAndEventDateGreaterThanEqualOrderByEventDateAsc(Schedule schedule, LocalDate date);
}
