package backend.zip.controller;


import backend.zip.service.EventService;
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
