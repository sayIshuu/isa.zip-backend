package backend.zip.dto.schedule.request;

import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.enums.Period;
import backend.zip.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddScheduleRequest {
    private Period period;
    private String moveDate;

    public Schedule toEntity(User user) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate parsedDate = LocalDate.parse(moveDate, formatter);

        return Schedule.builder()
                .user(user)
                .period(period)
                .moveDate(parsedDate)
                .build();
    }
}
