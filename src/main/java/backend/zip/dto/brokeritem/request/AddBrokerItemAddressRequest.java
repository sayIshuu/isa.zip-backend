package backend.zip.dto.brokeritem.request;


import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddBrokerItemAddressRequest {
    private String address;
    private String roadAddress;
    private String dong;
    private String roadDong;
    private String postNumber;
    private Double x;
    private Double y;

    public BrokerItem toEntity(User user,String address,String roadAddress, String dong,String roadDong,String postNumber, Double x, Double y) {
        return BrokerItem.builder()
                .user(user)
                .address(address)
                .roadAddress(roadAddress)
                .dong(dong)
                .roadDong(roadDong)
                .postNumber(postNumber)
                .x(x)
                .y(y)
                .build();
    }

}
