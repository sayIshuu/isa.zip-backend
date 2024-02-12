package backend.zip.controller.schedule;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.schedule.Event;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.dto.schedule.response.ScheduleResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.schedule.ScheduleService;
import backend.zip.service.schedule.EventService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users/schedule")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @Operation(summary = "일정 조회", description = "이사 일정 조회하는 API입니다.")
    @GetMapping()
    public ApiResponse<ScheduleResponse> getSchedule() {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        Schedule schedule = scheduleService.getScheduleByUserId(userId);


        ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
        return ApiResponse.onSuccess(scheduleResponse);

    }



    @PostMapping()
    @Operation(summary = "일정 등록", description = "이사 일정을 등록하는 API입니다.")
    public ApiResponse<ScheduleResponse> addSchedule(@RequestBody AddScheduleRequest request) {
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        // Create and persist the Schedule entity
        Schedule schedule = scheduleService.addSchedule(userId, request);
        ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
        return ApiResponse.onSuccess(scheduleResponse);
    }


    @PutMapping()
    @Operation(summary = "이사 일정 수정", description = "이사 일정을 수정하는 API입니다.")
    public ApiResponse<ScheduleResponse> updateSchedule(
            @RequestBody UpdateScheduleRequest request
    ) {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        Optional<Schedule> optionalSchedule = scheduleService.updateSchedule(userId, request);

        Schedule schedule = optionalSchedule.get();
        ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
        return ApiResponse.onSuccess(scheduleResponse);

    }

    @DeleteMapping()
    @Operation(summary = "일정 삭제", description = "이사 일정 삭제하는 API입니다.")
    @Transactional
    public ApiResponse<String> deleteSchedule() {
        //현재 로그인 중인 userId를 가져옴
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        scheduleService.deleteSchedule(userId);
        return ApiResponse.onSuccess("스케줄 삭제 성공 : "+userId);
    }
}

