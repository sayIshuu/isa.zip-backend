package backend.zip.domain.user.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ExtraFilter;
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
public class UserExtraFilter extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_extra_filter_id")
    private Long userExtraFilterId;

    @Column(name = "user_extra_filter")
    private ExtraFilter extraFilter;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;
}
