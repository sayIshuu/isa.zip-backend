package backend.zip.dto.brokeritem.response;


import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
public class BrokerItemDetailResponse {
    private List<ItemImage> itemImage;
    private ItemContent itemContent;

    public BrokerItemDetailResponse(List<ItemImage> itemImage,ItemContent itemContent) {
        this.itemImage = itemImage;
        this.itemContent = itemContent;
    }
}
