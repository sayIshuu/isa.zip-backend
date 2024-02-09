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

//    @DeleteMapping("/{eventId}")
//    public ApiResponse<String> deleteEvent(@PathVariable Long eventId) {
//        eventService.deleteEvent(eventId);
//        return ApiResponse.onSuccess("이벤트 삭제 성공"+eventId);
//    }
}
