package backend.zip.domain.user.options;

import backend.zip.domain.enums.Floor;
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
public class UserFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_floor_id")
    private Long userFloorId;

    @Column(name = "user_floor")
    private Floor floor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;

}
