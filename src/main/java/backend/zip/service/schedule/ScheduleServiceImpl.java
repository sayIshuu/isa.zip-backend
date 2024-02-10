package backend.zip.service.schedule;

import backend.zip.domain.enums.Period;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.repository.schedule.ScheduleRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Autowired
    private EventService eventService;

    @Override
    public Optional<Schedule> getScheduleByUserId(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        eventService.getEvents(userId);
        return (user != null) ? scheduleRepository.findByUser(user) : Optional.empty();
    }

    @Override
    public Schedule addSchedule(Long userId, AddScheduleRequest request) {
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            Schedule newSchedule = new Schedule(null, user, request.getPeriod(), request.getMoveDate());
            generateEventsForSchedule(newSchedule, request);
            return scheduleRepository.save(newSchedule);
        }

        return null;
    }

    @Override
    @Transactional
    public Optional<Schedule> updateSchedule(Long userId, UpdateScheduleRequest request) {
        return getScheduleByUserId(userId).map(existingSchedule -> {
            eventService.deleteEvents(existingSchedule.getScheduleId());
            existingSchedule.setMoveDate(request.getMoveDate());
            existingSchedule.setPeriod(request.getPeriod());
            UpdateEventsForSchedule(existingSchedule,request.getMoveDate(),request.getPeriod());
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


    private void generateEventsForSchedule(Schedule schedule, AddScheduleRequest request) {
        LocalDate moveDate = request.getMoveDate();
        Period movePeriod = schedule.getPeriod();
        switch (movePeriod) {
            case THREE_MONTHS:
                eventService.addEvent(schedule,moveDate.minusDays(90),"집주인에게 연락");
                eventService.addEvent(schedule,moveDate.minusDays(70),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(60),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(55),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(50),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(40),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case TWO_MONTHS:
                eventService.addEvent(schedule,moveDate.minusDays(60),"집주인에게 연락");
                eventService.addEvent(schedule,moveDate.minusDays(45),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(40),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(35),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(33),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(25),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case ONE_MONTH:
                eventService.addEvent(schedule,moveDate.minusDays(30),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(26),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(23),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(20),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(15),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case TWO_WEEKS:
                eventService.addEvent(schedule,moveDate.minusDays(15),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(14),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(10),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(10),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(8),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + schedule.getPeriod()); // 에러 처리 추후 수정
        }
    }

    private void UpdateEventsForSchedule(Schedule schedule,LocalDate moveDate, Period movePeriod) {
        switch (movePeriod) {
            case THREE_MONTHS:
                eventService.addEvent(schedule,moveDate.minusDays(90),"집주인에게 연락");
                eventService.addEvent(schedule,moveDate.minusDays(70),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(60),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(55),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(50),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(40),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case TWO_MONTHS:
                eventService.addEvent(schedule,moveDate.minusDays(60),"집주인에게 연락");
                eventService.addEvent(schedule,moveDate.minusDays(45),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(40),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(35),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(33),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(25),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case ONE_MONTH:
                eventService.addEvent(schedule,moveDate.minusDays(30),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(26),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(23),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(20),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(15),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            case TWO_WEEKS:
                eventService.addEvent(schedule,moveDate.minusDays(15),"이사 날짜 정하기, 예산 설정");
                eventService.addEvent(schedule,moveDate.minusDays(14),"공인중개사 컨택");
                eventService.addEvent(schedule,moveDate.minusDays(10),"방 확정");
                eventService.addEvent(schedule,moveDate.minusDays(10),"계약금 입금 및 이사 날짜 확정 논의");
                eventService.addEvent(schedule,moveDate.minusDays(8),"계약서 작성");
                eventService.addEvent(schedule,moveDate.minusDays(7),"짐 싸기, 전입 신고, 이사 업체 선정");
                eventService.addEvent(schedule,moveDate.minusDays(0),"가스, 전기, 수도요금 검침 및 정산");
                eventService.addEvent(schedule,moveDate.minusDays(0),"집 옮기기, 정돈");
                break;
            default:
                throw new IllegalArgumentException("Invalid period: " + schedule.getPeriod()); // 에러 처리 추후 수정
        }
    }

}
