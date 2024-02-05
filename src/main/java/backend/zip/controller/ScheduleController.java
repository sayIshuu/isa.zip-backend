package backend.zip.controller;

import backend.zip.domain.schedule.Schedule;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.dto.schedule.request.AddScheduleRequest;
import backend.zip.dto.schedule.request.UpdateScheduleRequest;
import backend.zip.dto.schedule.response.ScheduleResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ScheduleController {
    private final ScheduleService scheduleService;

    @GetMapping("{userId}/schedule")
    public ApiResponse<ScheduleResponse> getSchedule(@PathVariable Long userId) {
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

    @PostMapping("{userId}/schedule")
    public ApiResponse<ScheduleResponse> addSchedule(@PathVariable Long userId, @RequestBody AddScheduleRequest request) {

        Schedule schedule = scheduleService.addSchedule(userId, request);

        ScheduleResponse scheduleResponse = ScheduleResponse.fromEntity(schedule);
        return ApiResponse.onSuccess(scheduleResponse);
//        }
//        return null;
    }

    @PutMapping("{userId}/schedule")
    public ApiResponse<ScheduleResponse> updateSchedule(
            @PathVariable Long userId,
            @RequestBody UpdateScheduleRequest request
    ) {

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

    @DeleteMapping("{userId}/schedule")
    public ApiResponse<String> deleteSchedule(@PathVariable Long userId) {
        scheduleService.deleteSchedule(userId);
        return ApiResponse.onSuccess("스케줄 삭제 성공"+userId);
    }
}

