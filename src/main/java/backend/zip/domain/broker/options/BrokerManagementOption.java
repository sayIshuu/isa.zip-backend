package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ManagementOption;
import backend.zip.domain.user.UserOption;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class BrokerManagementOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_management_option_id")
    private Long brokerManagementOptionId;

    @Column(name = "broker_management_option")
    @Enumerated(EnumType.STRING)
    private ManagementOption managementOption;

    @Column(name = "management_price")
    private Integer managementPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;
}
