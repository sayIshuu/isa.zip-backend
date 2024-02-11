package backend.zip.dto.brokeritem.response;

import backend.zip.domain.item.ItemContent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemContentResponse {
    private Long itemContentId;
    private String shortIntroduction;
    private String specificIntroduction;

    public static ItemContentResponse of(ItemContent itemContent) {
        return ItemContentResponse.builder()
                .itemContentId(itemContent.getItemContentId())
                .shortIntroduction(itemContent.getShortIntroduction())
                .specificIntroduction(itemContent.getSpecificIntroduction())
                .build();
    }
}
