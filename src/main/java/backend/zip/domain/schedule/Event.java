package backend.zip.domain.schedule;

import backend.zip.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class Event extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long eventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id")
    private Schedule schedule;

    @Column(name = "event_title")
    private String eventTitle;

    public String getEventTitle()
    {
        return this.eventTitle;
    }

    public void setEventTitle(String eventTitle)
    {
        this.eventTitle = eventTitle;
    }
}
