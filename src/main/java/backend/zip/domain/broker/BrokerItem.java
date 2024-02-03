package backend.zip.domain.broker;

import backend.zip.domain.common.BaseEntity;
import backend.zip.domain.enums.ItemStatus;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.domain.user.User;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@Setter
//@DynamicInsert
//@DynamicUpdate
public class BrokerItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "broker_item_id")
    private Long brokerItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "address") //이 주소에는 추가로 ..동
    private String address;

    @Column(name = "dong")
    private String dong;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @Enumerated(EnumType.STRING) //매물 상태 그 때 뭐뭐 하기로 했죠....?
    private ItemStatus itemStatus;

    @OneToOne(mappedBy = "brokerItem",cascade = CascadeType.ALL)
    private ItemContent itemContent;

    @OneToMany(mappedBy = "brokerItem", cascade = CascadeType.ALL)
    private List<ItemImage> itemImages; // BrokerItem과 연결된 이미지들의 리스트

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "broker_option_id")
    private BrokerOption brokerOption;


//    public void setItemImages(List<ItemImage> itemImages) {
//        // 기존 이미지들의 참조를 제거
//        if (this.itemImages != null) {
//            this.itemImages.forEach(image -> image.setBrokerItem(null));
//        }
//        // 새로운 이미지 리스트와 이 BrokerItem 인스턴스의 연결을 설정
//        itemImages.forEach(image -> image.setBrokerItem(this));
//        // 이 BrokerItem의 itemImages 필드를 새로운 리스트로 업데이트
//        this.itemImages = itemImages;
//    }


    public void setDetails(List<ItemImage> itemImages, ItemContent itemContent) {
        this.itemImages = itemImages;
        this.itemContent = itemContent;
    }

    public void setBrokerOption(BrokerOption brokerOption) {
        if (brokerOption == null) {
            throw new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND);
        }
        this.brokerOption = brokerOption;
//        brokerOption.setBrokerItem(this);
    }

}

