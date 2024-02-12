package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.ManagementOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class BrokerManagementOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_management_option_id")
    private Long brokerManagementOptionId;

    @Column(name = "broker_management_option")
    @Enumerated(EnumType.STRING)
    private ManagementOption managementOption;

    @Column(name = "management_price")
    private String managementPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;
}
