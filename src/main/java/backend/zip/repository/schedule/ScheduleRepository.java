package backend.zip.Repository.schedule;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    //사용자를 기준으로 스케줄 조회
    Optional<Schedule> findByUser(User user);

    //사용자ID를 기준으로 스케줄 조회
    Optional<Schedule> findByUserId(Long userId);

    //사용자ID를 기준으로 스케줄 삭제
    void  deleteByUser(User user);


}
