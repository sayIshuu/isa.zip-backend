package backend.zip.dto.schedule.response;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.enums.Period;
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
public class ScheduleResponse {
    private Long scheduleId;
    private Long userId;
    private Period period;
    private String moveDate;

    public static ScheduleResponse fromEntity(Schedule schedule) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        String formattedDate = schedule.getMoveDate().format(formatter);

        return ScheduleResponse.builder()
                .scheduleId(schedule.getScheduleId())
                .userId(schedule.getUser() != null ? schedule.getUser().getId() : null)
                .period(schedule.getPeriod())
                .moveDate(formattedDate)
                .build();
    }
}
