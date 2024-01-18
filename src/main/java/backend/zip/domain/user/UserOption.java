package backend.zip.domain.user;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.*;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
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


}
