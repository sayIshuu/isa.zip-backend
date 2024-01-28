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
    private Long userId;
    private String address;
    private String dong;
    private Double x;
    private Double y;

    public BrokerItem toEntity(User user,String address, String dong, Double x, Double y) {
        return BrokerItem.builder()
                .user(user)
                .address(address)
                .dong(dong)
                .x(x)
                .y(y)
                .build();
    }

}
