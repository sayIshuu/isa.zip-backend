package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.enums.ItemStatus;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BrokerItemStatusResponse {
    private Long brokerItemId;
    private ItemStatus itemStatus;

    public static BrokerItemStatusResponse of(BrokerItem brokerItem) {
        return BrokerItemStatusResponse.builder()
                .brokerItemId(brokerItem.getBrokerItemId())
                .itemStatus(brokerItem.getItemStatus())
                .build();
    }
}
