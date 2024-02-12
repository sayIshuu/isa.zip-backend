package backend.zip.service.schedule;

import backend.zip.domain.enums.Period;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.User;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.global.exception.schedule.DuplicatedScheduleException;
import backend.zip.global.exception.schedule.ScheduleNotFoundException;
import backend.zip.global.exception.user.UserException;
import backend.zip.global.exception.user.UserNotFoundException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.schedule.ScheduleRepository;
import backend.zip.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static backend.zip.domain.enums.Role.ROLE_USER;
import static backend.zip.global.status.ErrorStatus.USER_NOT_FOUND;
import static backend.zip.global.status.ErrorStatus.SCHEDULE_NOT_FOUND;
import static backend.zip.global.status.ErrorStatus.DUPLICATED_SCHEDULE;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final UserRepository userRepository;
    @Autowired
    private EventService eventService;

    @Override
    public Schedule getScheduleByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        Optional<Schedule> schedule = scheduleRepository.findByUser(user);
        if (!schedule.isPresent()) {
            throw new ScheduleNotFoundException(SCHEDULE_NOT_FOUND);
        }
        return schedule.get();
    }

    @Override
    public Schedule addSchedule(Long userId, AddScheduleRequest request) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
        if (user.getRole() != ROLE_USER) {
            throw new UserException(ErrorStatus.SCHEDULE_NOT_REGISTERED);
        }
        try {
            getScheduleByUserId(userId);
            throw new DuplicatedScheduleException(DUPLICATED_SCHEDULE);
        } catch (ScheduleNotFoundException ex) {
            Schedule newSchedule = new Schedule(null, user, request.getPeriod(), request.getMoveDate());
            generateEventsForSchedule(newSchedule, request);
            return scheduleRepository.save(newSchedule);
        }
    }



    @Override
    @Transactional
    public Optional<Schedule> updateSchedule(Long userId, UpdateScheduleRequest request) {
        Schedule schedule = getScheduleByUserId(userId);
        if (schedule != null) {
            eventService.deleteEvents(schedule.getScheduleId());
            schedule.setMoveDate(request.getMoveDate());
            schedule.setPeriod(request.getPeriod());
            UpdateEventsForSchedule(schedule, request.getMoveDate(), request.getPeriod());
            return Optional.of(scheduleRepository.save(schedule));
        } else {
            throw new ScheduleNotFoundException(SCHEDULE_NOT_FOUND);
        }
    }



    @Override
    public void deleteSchedule(Long userId) {
        User deleteUser = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException(USER_NOT_FOUND));

        if (deleteUser != null) {
            Schedule existingSchedule = getScheduleByUserId(userId);
            if (existingSchedule != null) {
                eventService.deleteEvents(existingSchedule.getScheduleId());
            }
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
