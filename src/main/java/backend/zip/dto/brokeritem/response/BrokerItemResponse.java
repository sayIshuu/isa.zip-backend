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
public class BrokerItemResponse {
    private Long brokerItemId;
    private ItemStatus itemStatus;
    private BrokerItemAddressResponse addressResponse;
    private BrokerItemDetailResponse detailResponse;
    private BrokerItemOptionResponse optionResponse;

    public BrokerItemResponse(Long brokerItemId,ItemStatus itemStatus, BrokerItemAddressResponse addressResponse,
                              BrokerItemDetailResponse detailResponse,
                              BrokerItemOptionResponse optionResponse) {
        this.brokerItemId = brokerItemId;
        this.itemStatus = itemStatus;
        this.addressResponse = addressResponse;
        this.detailResponse = detailResponse;
        this.optionResponse = optionResponse;
    }

    public static BrokerItemResponse getBrokerItemResponse(BrokerItem savedBrokerItem, BrokerItemAddressResponse addressResponse) {
        List<ItemImage> itemImages = savedBrokerItem.getItemImages();
        ItemContent itemContent = savedBrokerItem.getItemContent();
        BrokerItemDetailResponse detailResponse = BrokerItemDetailResponse.of(itemImages, itemContent);

        BrokerOption brokerOption = savedBrokerItem.getBrokerOption();
        BrokerItemOptionResponse optionResponse = BrokerItemOptionResponse.of(brokerOption);

        BrokerItemResponse itemResponse = new BrokerItemResponse(savedBrokerItem.getBrokerItemId(),
                savedBrokerItem.getItemStatus(),
                addressResponse, detailResponse, optionResponse);

        return itemResponse;
    }


    public static BrokerItemResponse of(BrokerItem brokerItem) {
        BrokerItemAddressResponse addressResponse = BrokerItemAddressResponse.of(
                brokerItem.getAddress(),
                brokerItem.getRoadAddress(),
                brokerItem.getDong(),
                brokerItem.getRoadDong(),
                brokerItem.getPostNumber(),
                brokerItem.getX(),
                brokerItem.getY()
        );

        BrokerItemDetailResponse detailResponse = BrokerItemDetailResponse.of(
                brokerItem.getItemImages(),
                brokerItem.getItemContent()
        );

        BrokerItemOptionResponse optionResponse = BrokerItemOptionResponse.of(brokerItem.getBrokerOption());

        return new BrokerItemResponse(brokerItem.getBrokerItemId(),brokerItem.getItemStatus(), addressResponse, detailResponse, optionResponse);
    }


    public static BrokerItemResponse from(Matching matching) {
        return BrokerItemResponse.of(matching.getBrokerItem());
    }
}
