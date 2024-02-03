package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class BrokerItemResponse {
    private BrokerItemAddressResponse addressResponse;
    private BrokerItemDetailResponse detailResponse;
    private BrokerItemOptionResponse optionResponse;

    public BrokerItemResponse(BrokerItemAddressResponse addressResponse,
                              BrokerItemDetailResponse detailResponse,
                              BrokerItemOptionResponse optionResponse) {
        this.addressResponse = addressResponse;
        this.detailResponse = detailResponse;
        this.optionResponse = optionResponse;
    }

    public static BrokerItemResponse getBrokerItemResponse(BrokerItem savedBrokerItem, BrokerItemAddressResponse addressResponse) {
        // BrokerDetailResponse 생성
        List<ItemImage> itemImages = savedBrokerItem.getItemImages();
        ItemContent itemContent = savedBrokerItem.getItemContent();
        BrokerItemDetailResponse detailResponse = new BrokerItemDetailResponse(itemImages, itemContent);

        // BrokerOptionResponse 생성
        BrokerOption brokerOption = savedBrokerItem.getBrokerOption();
        BrokerItemOptionResponse optionResponse = new BrokerItemOptionResponse(brokerOption);

        // BrokerItemResponse 생성
        BrokerItemResponse itemResponse = new BrokerItemResponse(addressResponse, detailResponse, optionResponse);

        return itemResponse;
    }
}
