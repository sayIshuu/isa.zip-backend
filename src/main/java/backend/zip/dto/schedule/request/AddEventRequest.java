package backend.zip.dto.schedule.request;

import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.enums.Period;
import backend.zip.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddEventRequest {
    private String eventTitle;

    public Event toEntity(Schedule schedule) {
        return Event.builder()
                .schedule(schedule)
                .eventTitle(eventTitle)
                .build();
    }

}
