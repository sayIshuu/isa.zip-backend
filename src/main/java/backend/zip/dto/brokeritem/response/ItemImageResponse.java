package backend.zip.dto.brokeritem.response;

import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemImageResponse {
    private Long itemImageId;
    private String imageUrl;

    public static ItemImageResponse of(ItemImage itemImage) {
        return ItemImageResponse.builder()
                .itemImageId(itemImage.getItemImageId())
                .imageUrl(itemImage.getItemImage())
                .build();
    }

}
