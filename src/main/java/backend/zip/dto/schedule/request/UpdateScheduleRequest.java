package backend.zip.dto.schedule.request;

import backend.zip.domain.enums.Period;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateScheduleRequest {

    private Period period;

    private String moveDate;
}
