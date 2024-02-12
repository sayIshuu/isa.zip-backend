package backend.zip.domain.user.options;


import backend.zip.domain.enums.RoomType;
import backend.zip.domain.user.UserOption;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class UserRoomType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_room_type_id")
    private Long userRoomTypeId;

    @Column(name = "user_room_type")
    private RoomType roomType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;

}
