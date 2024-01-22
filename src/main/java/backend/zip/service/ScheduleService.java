package backend.zip.service;

import backend.zip.Repository.ScheduleRepository;
import backend.zip.domain.schedule.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    //조회
    public Optional<Schedule> getScheduleByUserId(Long userId) {
        return scheduleRepository.findByUserId(userId);
    }

    //생성
    public void createSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
    }

    //수정
    public Optional<Schedule> updateSchedule(Long userId, Schedule updatedSchedule) {
        return getScheduleByUserId(userId).map(existingSchedule -> {
            existingSchedule.setMoveDate(updatedSchedule.getMoveDate());
            existingSchedule.setPeriod(updatedSchedule.getPeriod());
            return scheduleRepository.save(existingSchedule);
        });
    }
    //삭제
    public void deleteSchedule(Long userId) {
        scheduleRepository.deleteByUserId(userId);
    }

}
