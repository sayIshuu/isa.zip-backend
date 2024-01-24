package backend.zip.service;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.repository.ScheduleRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    //조회
    public Optional<Schedule> getScheduleByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: "));

        return scheduleRepository.findByUser(user);
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
        User deleteUser = userRepository.findById(userId).get();
        scheduleRepository.deleteByUser(deleteUser);
    }

}
