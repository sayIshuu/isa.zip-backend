package backend.zip.controller.schedule;

import backend.zip.domain.schedule.Schedule;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.dto.schedule.response.ScheduleResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.schedule.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping()
    public ApiResponse<ScheduleResponse> getSchedule() {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        Optional<Schedule> optionalSchedule = scheduleService.getScheduleByUserId(userId);

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
            return ApiResponse.onSuccess(scheduleResponse);
        }
        return null;
//        else {
//            // Handle the case where the schedule is not found
//            return ApiResponse.onFailure("Schedule not found for user with ID: " + userId);
//        }
    }

    @PostMapping()
    public ApiResponse<ScheduleResponse> addSchedule(@RequestBody AddScheduleRequest request) {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        Schedule schedule = scheduleService.addSchedule(userId, request);

        ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
        return ApiResponse.onSuccess(scheduleResponse);
//        }
//        return null;
    }

    @PutMapping()
    public ApiResponse<ScheduleResponse> updateSchedule(
            @RequestBody UpdateScheduleRequest request
    ) {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        Optional<Schedule> optionalSchedule = scheduleService.updateSchedule(userId, request);

        if (optionalSchedule.isPresent()) {
            Schedule schedule = optionalSchedule.get();
            ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
            return ApiResponse.onSuccess(scheduleResponse);
        }
        return null;
//        else {
//            // Handle the case where the schedule is not found
//            return ApiResponse.onFailure("Schedule update failed for user with ID: " + userId);
//        }
    }

//    @DeleteMapping()
//    public ApiResponse<String> deleteSchedule() {
//        //현재 로그인 중인 userId를 가져옴
//        String loggedInUserId = SecurityUtils.getLoggedInUserId();
//        Long userId = Long.valueOf(loggedInUserId);
//        scheduleService.deleteSchedule(userId);
//        return ApiResponse.onSuccess("스케줄 삭제 성공"+userId);
//    }
}

