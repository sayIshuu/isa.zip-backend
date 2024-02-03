package backend.zip.dto.brokeritem.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BrokerItemAddressResponse {
    private String addressName;
    private String roadName;
    private String postNumber;
    private String dong;
    private String roadDong;
    private Double x;
    private Double y;

    public static BrokerItemAddressResponse of(String addressName, String roadName,String postNumber, String dong, String roadDong,Double x, Double y) {
        return BrokerItemAddressResponse.builder()
                .addressName(addressName)
                .roadName(roadName)
                .postNumber(postNumber)
                .dong(dong)
                .roadDong(roadDong)
                .x(x)
                .y(y)
                .build();
    }

}
