package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.DealType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BrokerDealType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_deal_id")
    private Long brokerDealId;

    @Enumerated(EnumType.STRING)
    @Column(name = "deal_type")
    private DealType dealType;

    @Column(name = "charter_price")
    private String charterPrice;

    @Column(name = "trading_price")
    private String tradingPrice;

    @Column(name = "month_price")
    private String monthPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;

}


