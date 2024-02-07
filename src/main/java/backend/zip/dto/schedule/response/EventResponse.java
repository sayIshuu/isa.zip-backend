package backend.zip.dto.schedule.response;

import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {

    private Long eventId;
    private Long scheduleId;
    private String eventTitle;
    private LocalDate eventDate;

    public static EventResponse fromEntity(Event event) {
        return EventResponse.builder()
                .eventId(event.getEventId())
                .scheduleId(event.getSchedule() != null ? event.getSchedule().getScheduleId() : null)
                .eventTitle(event.getEventTitle())
                .eventDate(event.getEventDate())
                .build();
    }
}
