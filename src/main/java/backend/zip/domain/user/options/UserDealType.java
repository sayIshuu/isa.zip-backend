package backend.zip.domain.user.options;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.DealType;
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
public class UserDealType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_deal_id")
    private Long dealId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    @JoinColumn(name = "user_option_id")
    private UserOption userOption;

    @Enumerated(EnumType.STRING)
    private DealType dealType;

    @Column(name = "min_price")
    private Integer minPrice;

    @Column(name = "max_price")
    private Integer maxPrice;

    @Column(name = "min_deposit")
    private Integer minDeposit;

    @Column(name = "max_deposit")
    private Integer maxDeposit;

    @Column(name = "min_month_price")
    private Integer minMonthPrice;

    @Column(name = "max_month_price")
    private Integer maxMonthPrice;
}
