package backend.zip.domain.broker;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class Broker extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "business_num")
    private String businessNum;

    @OneToOne(mappedBy = "broker", cascade = CascadeType.ALL)    //Json 무한 루프 문제 발생
    private User user;                                           //@JsonBackReference를 사용하면 Broker에 User 접근도 안됨

}
