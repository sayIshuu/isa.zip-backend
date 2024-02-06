package backend.zip.dto.schedule.request;

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
public class AddScheduleRequest {
    private Period period;

    private LocalDate moveDate;

    public Schedule toEntity(User user) {
        return Schedule.builder()
                .user(user)
                .period(period)
                .moveDate(moveDate)
                .build();
    }
}
