package backend.zip.dto.brokeritem.response;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.options.BrokerDealType;
import backend.zip.domain.broker.options.BrokerFloor;
import backend.zip.domain.broker.options.BrokerManagementOption;
import backend.zip.domain.enums.ItemStatus;
import backend.zip.domain.item.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class BrokerItemShowResponse {
    private Integer count;
    private List<BrokerItemList> brokerItemListList;

    @Getter
    @AllArgsConstructor
    public static class BrokerItemList {
        private Long brokerItemId;
        private Double x;
        private Double y;
        private ItemStatus itemStatus;
        private List<BrokerDealType> dealTypes;
        private String roomSize;
        private List<BrokerFloor> floors;
        private List<BrokerManagementOption> managementOptions;
        private String addressName;
        private String roadName;
        private String shortIntroduction;
        private List<ItemImage> itemImage;
    }

    public static BrokerItemShowResponse of(List<BrokerItem> brokerItemList) {
        List<BrokerItemList> brokerItemLists = brokerItemList.stream()
                .map(brokerItem -> new BrokerItemList(brokerItem.getBrokerItemId(),
                        brokerItem.getX(),
                        brokerItem.getY(),
                        brokerItem.getItemStatus(),
                        brokerItem.getBrokerOption().getBrokerDealTypes(),
                        brokerItem.getBrokerOption().getRoomSize(),
                        brokerItem.getBrokerOption().getBrokerFloors(),
                        brokerItem.getBrokerOption().getBrokerManagementOptions(),
                        brokerItem.getAddress(), brokerItem.getRoadAddress(),
                        brokerItem.getItemContent().getShortIntroduction(),
                        brokerItem.getItemImages())).collect(Collectors.toList());

        return BrokerItemShowResponse.builder()
                .count(brokerItemLists.size())
                .brokerItemListList(brokerItemLists)
                .build();
    }
}
