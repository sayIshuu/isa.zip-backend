package backend.zip.controller.schedule;


import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import backend.zip.dto.schedule.request.UpdateEventRequest;
import backend.zip.dto.schedule.response.EventResponse;
import backend.zip.dto.schedule.response.ScheduleResponse;
import backend.zip.global.apipayload.ApiResponse;
import backend.zip.security.SecurityUtils;
import backend.zip.service.schedule.EventService;
import backend.zip.service.schedule.ScheduleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users/events")
public class EventController {
    private final EventService eventService;

    @GetMapping()
    @Operation(summary = "상세 일정 조회", description = "상세 일정을 조회하는 API입니다.")
    public ApiResponse<List<EventResponse>> getEvents() {
        String loggedInUserId = SecurityUtils.getLoggedInUserId();
        Long userId = Long.valueOf(loggedInUserId);
        List<Event> eventList = eventService.getEvents(userId);
        List<EventResponse> eventResponses = new ArrayList<>();
        for (int i = 0; i < eventList.size(); i++) {
            eventResponses.add(EventResponse.fromEntity(eventList.get(i)));
        }
        return ApiResponse.onSuccess(eventResponses);
        // Note: The following line will never be executed after the return statement
        // EventResponse eventResponse = EventResponse.fromEntity(event);
        // TODO: Handle errors
    }

    @PutMapping("/{eventId}")
    @Operation(summary = "상세 일정 수정", description = "상세 일정(1개)을 수정하는 API입니다.")
    public ApiResponse<EventResponse> updateSchedule(
            @RequestBody UpdateEventRequest request
    )
    {
        Optional<Event> optionalEvent = eventService.updateEvent(request);

        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            EventResponse eventResponse = EventResponse.fromEntity(event);
            return ApiResponse.onSuccess(eventResponse);
        }
        return null;
//        else {
//            // Handle the case where the schedule is not found
//            return ApiResponse.onFailure("Schedule update failed for user with ID: " + userId);
//        }
    }

    @DeleteMapping("/{eventId}")
    @Operation(summary = "상세 일정 삭제", description = "상세 일정(1개)을 삭제하는 API입니다.")
    public ApiResponse<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ApiResponse.onSuccess("이벤트 삭제 성공"+eventId);
    }
}
