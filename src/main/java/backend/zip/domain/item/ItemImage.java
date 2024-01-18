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
public class ItemImage extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long itemImageId;

    @Column(name = "item_image")
    private String itemImage;

    @OneToOne
    @JoinColumn(name = "broker_id")
    private BrokerItem brokerItem;

}
