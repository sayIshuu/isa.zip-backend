package backend.zip.domain.broker.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ExtraFilter;
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
public class BrokerExtraFilter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_extra_filter_id")
    private Long brokerExtraFilterId;

    @Column(name = "broker_extra_filter")
    private ExtraFilter extraFilter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_option_id")
    private UserOption brokerOption;

}
