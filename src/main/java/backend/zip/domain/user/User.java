package backend.zip.domain.user;

import backend.zip.domain.broker.Broker;
import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@DynamicInsert
//@DynamicUpdate
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //기본 키이며 자동으로 1씩 증가함
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_id")
    private Broker broker;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "email",nullable = false)
    private String email;

    @Column(name = "password",nullable = false)
    private String password;

    @Column(name = "nickname",nullable = false)
    private String nickName;

    @Column(name = "is_social")
    private Boolean isSocial;

    @Column(name = "review_point")
    private Integer reviewPoint;

}
