package backend.zip.dto.user.request;

import backend.zip.domain.enums.Role;
import backend.zip.domain.user.User;
import lombok.Builder;
import lombok.Getter;

public class UserRequest {
    @Getter
    public static class BrokerRequest {
        Long brokerId;
        String name;
        String phoneNum;
        String businessName;
    }
}
