package backend.zip.domain.item;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class ItemContent extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_content_id")
    private Long itemContentId;

    @OneToOne
    @JoinColumn(name = "broker_id")
    private BrokerItem brokerItem;

    @Column(name = "simple_content")
    private String simpleContent;

    @Column(name = "spec_content")
    private String specificContent;

}
