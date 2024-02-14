package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.enums.ItemStatus;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.domain.match.Matching;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BrokerItemNoAddressResponse {
    private Long brokerItemId;
    private String businessName;
    private ItemStatus itemStatus;
    private String dongName;
    private BrokerItemDetailResponse detailResponse;
    private BrokerItemOptionResponse optionResponse;

    public BrokerItemNoAddressResponse(Long brokerItemId,
                                       String businessName,
                                       ItemStatus itemStatus,
                                       String dongName,
                                       BrokerItemDetailResponse detailResponse,
                                       BrokerItemOptionResponse optionResponse) {
        this.brokerItemId = brokerItemId;
        this.businessName = businessName;
        this.itemStatus = itemStatus;
        this.dongName = dongName;
        this.detailResponse = detailResponse;
        this.optionResponse = optionResponse;
    }


    public static BrokerItemNoAddressResponse of(BrokerItem brokerItem) {

        BrokerItemDetailResponse detailResponse = BrokerItemDetailResponse.of(
                brokerItem.getItemImages(),
                brokerItem.getItemContent()
        );

        BrokerItemOptionResponse optionResponse = BrokerItemOptionResponse.of(brokerItem.getBrokerOption());

        return new BrokerItemNoAddressResponse(brokerItem.getBrokerItemId(),
                brokerItem.getUser().getBroker().getBusinessName(),
                brokerItem.getItemStatus(),
                brokerItem.getDong(),
                detailResponse, optionResponse);
    }


    public static BrokerItemNoAddressResponse from(Matching matching) {
        return BrokerItemNoAddressResponse.of(matching.getBrokerItem());
    }
}
