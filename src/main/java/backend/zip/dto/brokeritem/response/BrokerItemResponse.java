package backend.zip.dto.brokeritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BrokerItemResponse {
    private BrokerItemAddressResponse addressResponse;
    private BrokerItemOptionResponse optionResponse;

    public BrokerItemResponse(BrokerItemAddressResponse addressResponse, BrokerItemOptionResponse optionResponse) {
        this.addressResponse = addressResponse;
        this.optionResponse = optionResponse;
    }
}
