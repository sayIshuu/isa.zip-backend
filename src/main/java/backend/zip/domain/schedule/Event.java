package backend.zip.domain.schedule;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_title")
    private String eventTitle;

    public void setSchedule(Schedule schedule)
    {
        this.schedule = schedule;
    }

    public void setEventTitle(String eventTitle)
    {
        this.eventTitle = eventTitle;
    }

    public void setEventDate(LocalDate eventDate)
    {
        this.eventDate = eventDate;
    }

    public static Event toEntity(Schedule schedule, LocalDate eventDate, String eventTitle)
    {
        return Event.builder()
                .schedule(schedule)
                .eventDate(eventDate)
                .eventTitle(eventTitle)
                .build();
    }
}
