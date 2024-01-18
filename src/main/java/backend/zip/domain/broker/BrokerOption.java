package backend.zip.domain.broker;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class BrokerOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_option_id")
    private Long brokerOptionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_item_id")
    private BrokerItem brokerItem;

    @Column(name = "broker_room_size")
    private String roomSize;

//    @Temporal(TemporalType.DATE) //LocalDate 쓸거면 이거 안써도 됨
    @Column(name = "broker_approved_date")
    private LocalDate approveDate;

    @Column(name = "management_price")
    private Integer managementPrice;

}
