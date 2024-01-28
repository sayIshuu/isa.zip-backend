package backend.zip.dto.brokeritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BrokerItemAddressResponse {
    private String address;
    private String dong;
    private Double x;
    private Double y;

    public BrokerItemAddressResponse(String address, String dong, Double x, Double y) {
        this.address = address;
        this.dong = dong;
        this.x = x;
        this.y = y;
    }
}
