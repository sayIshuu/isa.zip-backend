package backend.zip.domain.match;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.user.UserItem;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class Matching extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "match_id")
    private Long matchID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_item_id")
    private UserItem userItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_item_id")
    private BrokerItem brokerItem;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

}
