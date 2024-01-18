package backend.zip.domain.broker;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.DealType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BrokerDealType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_deal_id")
    private Long brokerDealId;

    @Column(name = "broker_option_id")
    private Long brokerOptionId;

    @Column(name = "deal_type")
    private DealType dealType;

    @Column(name = "price")
    private Integer price;

    @Column(name = "deposit")
    private Integer deposit;

    @Column(name = "month_price")
    private Integer monthPrice;

}


