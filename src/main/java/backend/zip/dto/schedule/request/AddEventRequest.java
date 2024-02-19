package backend.zip.dto.schedule.request;

import backend.zip.domain.enums.schedule.Event;
import backend.zip.domain.enums.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
