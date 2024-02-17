package backend.zip.domain.broker;

import backend.zip.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Broker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_id")
    private Long brokerId;

    @Column(name = "name")
    private String name;

    @Column(name = "phone_num")
    private String phoneNum;

    @Column(name = "business_name")
    private String businessName;

    @OneToOne(mappedBy = "broker", cascade = CascadeType.ALL)
    private User user;
}
