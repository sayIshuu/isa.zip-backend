package backend.zip.domain.broker.options;

import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.InternalFacility;
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
public class BrokerInternalFacility extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_internal_facility_id")
    private Long brokerInternalFacilityId;

    @Column(name = "broker_internal_facility")
    @Enumerated(EnumType.STRING)
    private InternalFacility internalFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;
}
