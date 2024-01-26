package backend.zip.controller;

import backend.zip.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class ScheduleController {
    private final ScheduleService scheduleService;

//    @Autowired
//    public ScheduleController(ScheduleService scheduleService) {
//        this.scheduleService = scheduleService;
//    }
}
