package backend.zip.dto.brokeritem.request;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBrokerItemDetailsRequest {
    private MultipartFile[] brokerItemImg;
    private String shortsIntroduction;
    private String detailExplanation;

//    public BrokerItem toEntity() {
//        return BrokerItem.builder()
//                .shortsIntroduction(shortsIntroduction)
//                .detailExplanation(detailExplanation)
//                .build();
//    }

}
