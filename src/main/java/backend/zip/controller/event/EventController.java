package backend.zip.controller.event;


import backend.zip.service.event.EventService;
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
