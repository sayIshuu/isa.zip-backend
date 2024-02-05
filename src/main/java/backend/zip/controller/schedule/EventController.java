package backend.zip.controller.schedule;


import backend.zip.service.schedule.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

//    public EventController(EventService eventService) {
//        this.eventService = eventService;
//    }
}
