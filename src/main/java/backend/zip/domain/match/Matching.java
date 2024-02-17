package backend.zip.domain.match;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.user.UserItem;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import static backend.zip.domain.enums.MatchStatus.WAITING;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class Matching {
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

//    @Column(name = "match_dong")
    private String matchDong;

    //private boolean isLiked;

    @Enumerated(EnumType.STRING)
    @Column(name = "match_status")
    private MatchStatus matchStatus;

    public static Matching createMatching(UserItem userItem, BrokerItem brokerItem) {
        return Matching.builder()
                .userItem(userItem)
                .brokerItem(brokerItem)
                .matchStatus(WAITING)
                .matchDong(userItem.getDong())
                //.isLiked(false)
                .build();
    }

    public void updateMatchStatus(MatchStatus matchStatus) {
        this.matchStatus = matchStatus;
    }

    //public void updateLiked(boolean b) {this.isLiked = b;}
}
