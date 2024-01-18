package backend.zip.domain.user.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.RoomSize;
import backend.zip.domain.user.UserOption;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class UserRoomSize extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_room_size_id")
    private Long userRoomSize;

    @Column(name = "user_room_size")
    private RoomSize roomSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;
}
