package backend.zip.dto.brokeritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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
}
