package backend.zip.dto.schedule.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateEventRequest {
    private Long eventId;
    private String eventTitle;
    private LocalDate eventDate;
}
