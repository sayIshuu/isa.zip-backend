package backend.zip.repository.schedule;

import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event,Long> {
    //schedule로 조회
    List<Event> findBySchedule(Schedule schedule);
    Event findFirstByScheduleAndEventDateGreaterThanEqualOrderByEventDateAsc(Schedule schedule, LocalDate date);
}
