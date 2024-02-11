package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.DealType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type")
    private DealType dealType;

    @Column(name = "price")
    private String price;

    @Column(name = "deposit")
    private String deposit;

    @Column(name = "month_price")
    private String monthPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;

}


