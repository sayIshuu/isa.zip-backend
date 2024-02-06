package backend.zip.domain.schedule;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.Period;
import backend.zip.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class Schedule extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private Long scheduleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "period")
    private Period period;

    @Temporal(TemporalType.DATE)
    @Column(name = "move_date")
    private LocalDate moveDate;

    public void setMoveDate(LocalDate moveDate) {
        this.moveDate = moveDate;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }
}
