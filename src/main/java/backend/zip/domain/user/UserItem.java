package backend.zip.domain.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class UserItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_item_id")
    private Long userItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    //@Column(name = "address")
    //private String address;

    @Column(name = "dong")
    private String dong;

    @Column(name = "is_matched")
    private Boolean isMatched;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_option_id")
    @JsonManagedReference
    private UserOption userOption;
    public void setUserOption(UserOption userOption) {
        // 예외처리 not found
        if (userOption == null) {
            throw new IllegalArgumentException("UserOption이 존재하지 않습니다.");
        }
        this.userOption = userOption;
    }
}
