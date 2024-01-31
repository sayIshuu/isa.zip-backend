package backend.zip.dto.brokeritem.request;

import backend.zip.domain.item.ItemContent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBrokerItemDetailsRequest {
    private MultipartFile[] brokerItemImg;
    private String shortIntroduction;
    private String specificIntroduction;

}
