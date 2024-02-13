package backend.zip.domain.item;

import backend.zip.domain.broker.BrokerItem;
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
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_image_id")
    private Long itemImageId;

    @Column(name = "item_image")
    private String itemImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_item_id")
    @JsonBackReference
    private BrokerItem brokerItem;

//    public void setBrokerItem(BrokerItem brokerItem) {
//        this.brokerItem = brokerItem;
//    }

    public ItemImage(String itemImage,BrokerItem brokerItem) {
        this.itemImage = itemImage;
        this.brokerItem = brokerItem;
    }

}
