package backend.zip.domain.user.options;

import backend.zip.domain.enums.InternalFacility;
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
public class UserInternalFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_internal_facility_id")
    private Long userInternalFacilityId;

    @Column(name = "user_internal_facility")
    private InternalFacility internalFacility;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;
}
