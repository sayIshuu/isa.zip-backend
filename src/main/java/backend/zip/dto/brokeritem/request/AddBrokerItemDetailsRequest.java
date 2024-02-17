package backend.zip.dto.brokeritem.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBrokerItemDetailsRequest {
    private String shortIntroduction;
    private String specificIntroduction;

}
