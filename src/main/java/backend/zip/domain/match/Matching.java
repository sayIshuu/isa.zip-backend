package backend.zip.domain.match;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.user.UserItem;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

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

    @Column(name = "match_dong") //매칭테이블에서 동으로 필터링 할 때(편의상)쓰이는 동
    private String matchDong;

    @Enumerated(EnumType.STRING)
    private MatchStatus matchStatus;

    public static Matching createMatching(UserItem userItem, BrokerItem brokerItem) {
        return Matching.builder()
                .matchStatus(MatchStatus.WAITING)
                .matchDong(userItem.getDong())
                .userItem(userItem)
                .brokerItem(brokerItem)
                .build();
    }

}
