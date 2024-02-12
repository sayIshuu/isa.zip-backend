package backend.zip.domain.item;

import backend.zip.domain.broker.BrokerItem;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class ItemContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_content_id")
    private Long itemContentId;

    @OneToOne
    @JsonBackReference
    @JoinColumn(name = "broker_item_id")
    private BrokerItem brokerItem;

    @Column(name = "short_introduction")
    private String shortIntroduction;

    @Column(name = "specific_introduction")
    private String specificIntroduction;

    public ItemContent(String shortIntroduction, String specificIntroduction, BrokerItem brokerItem) {
        this.shortIntroduction = shortIntroduction;
        this.specificIntroduction = specificIntroduction;
        this.brokerItem = brokerItem;
    }

    public void setBrokerItem(BrokerItem brokerItem) {
        this.brokerItem = brokerItem;
    }

    public void updateDetails(String shortIntroduction, String specificIntroduction) {
        this.shortIntroduction = shortIntroduction;
        this.specificIntroduction = specificIntroduction;
    }
}
