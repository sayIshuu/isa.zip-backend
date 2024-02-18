package backend.zip.dto.schedule.response;

import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventResponse {
    private Long eventId;
    private Long scheduleId;
    private String eventTitle;
    private String eventDate;

    public static EventResponse fromEntity(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = event.getEventDate().format(formatter);

        return EventResponse.builder()
                .eventId(event.getEventId())
                .scheduleId(event.getSchedule() != null ? event.getSchedule().getScheduleId() : null)
                .eventTitle(event.getEventTitle())
                .eventDate(formattedDate)  // 포맷된 날짜를 사용
                .build();
    }
}