package backend.zip.dto.useritem.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserItemAddressResponse {
    private String address;
    private String dong;

    public UserItemAddressResponse(String address, String dong) {
        this.address = address;
        this.dong = dong;
    }

    public static UserItemAddressResponse from(String address, String dong) {
        return new UserItemAddressResponse(address, dong);
    }
}
