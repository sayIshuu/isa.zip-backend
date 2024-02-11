package backend.zip.dto.brokeritem.response;


import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@AllArgsConstructor
//@Builder
public class BrokerItemDetailResponse {
    private List<ItemImageResponse> itemImages;
    private ItemContentResponse itemContent;

    public static BrokerItemDetailResponse of(List<ItemImage> itemImages, ItemContent itemContent) {
        List<ItemImageResponse> imageUrls = itemImages.stream()
                .map(itemImage -> ItemImageResponse.of(itemImage) )
                .collect(Collectors.toList());
        ItemContentResponse itemContentResponse = ItemContentResponse.of(itemContent);

        return new BrokerItemDetailResponse(imageUrls, itemContentResponse);
    }
//
}
