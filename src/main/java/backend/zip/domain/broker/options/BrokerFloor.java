package backend.zip.domain.broker.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.Floor;
import backend.zip.domain.user.UserOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class BrokerFloor extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_floor_id")
    private Long brokerFloorId;

    @Column(name = "broker_floor")
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_option_id")
    private UserOption brokerOption;
}
