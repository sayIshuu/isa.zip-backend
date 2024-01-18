package backend.zip.domain.broker.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ManagementOption;
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
public class BrokerManagementOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_management_option_id")
    private Long brokerManagementOptionId;

    @Column(name = "broker_management_option")
    private ManagementOption managementOption;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_option_id")
    private UserOption brokerOption;
}
