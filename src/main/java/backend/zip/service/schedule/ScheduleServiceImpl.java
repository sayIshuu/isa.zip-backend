package backend.zip.service.schedule;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.repository.ScheduleRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<Schedule> getScheduleByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        return (user != null) ? scheduleRepository.findByUser(user) : Optional.empty();
    }

    @Override
    public Schedule addSchedule(Long userId, AddScheduleRequest request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Schedule newSchedule = new Schedule(null, user, request.getPeriod(), request.getMoveDate());
            return scheduleRepository.save(newSchedule);
        }

        return null;
    }

    @Override
    public Optional<Schedule> updateSchedule(Long userId, UpdateScheduleRequest request) {
        return getScheduleByUserId(userId).map(existingSchedule -> {
            existingSchedule.setMoveDate(request.getMoveDate());
            existingSchedule.setPeriod(request.getPeriod());
            return scheduleRepository.save(existingSchedule);
        });
    }

    @Override
    public void deleteSchedule(Long userId) {
        User deleteUser = userRepository.findById(userId).orElse(null);

        if (deleteUser != null) {
            scheduleRepository.deleteByUser(deleteUser);
        }
    }
}
