package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ExtraFilter;
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
public class BrokerExtraFilter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_extra_filter_id")
    private Long brokerExtraFilterId;

    @Column(name = "broker_extra_filter")
    @Enumerated(EnumType.STRING)
    private ExtraFilter extraFilter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;

}
