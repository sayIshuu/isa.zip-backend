package backend.zip.domain.user;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ApproveDate;
import backend.zip.domain.user.options.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UserOption extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_option_id")
    private Long userOptionId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_item_id")
    private UserItem userItem;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved_date")
    private ApproveDate approveDate;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserDealType> userDealTypes;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserManagementOption> userManagementOptions;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserInternalFacility> userInternalFacilities;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserExtraFilter> userExtraFilters;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserFloor> userFloors;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserRoomType> userRoomTypes;

    @OneToMany(mappedBy = "userOption", cascade = CascadeType.ALL)
    private List<UserRoomSize> userRoomSizes;
}
