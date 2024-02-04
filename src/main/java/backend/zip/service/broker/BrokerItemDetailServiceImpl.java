package backend.zip.service.broker;

import backend.zip.config.AmazonConfig;
import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.domain.s3.Uuid;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemDetailResponse;
import backend.zip.global.aws.s3.AmazonS3Manager;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.UuidRepository;
import backend.zip.repository.broker.BrokerItemContentRepository;
import backend.zip.repository.broker.BrokerItemImageRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrokerItemDetailServiceImpl implements BrokerItemDetailService {
    private final AmazonS3Manager amazonS3Manager;
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerItemContentRepository brokerItemContentRepository;
    private final BrokerItemImageRepository brokerItemImageRepository;
    private final UuidRepository uuidRepository;

    @Override
    public ItemContent saveBrokerItemContentDetails(AddBrokerItemDetailsRequest addBrokerItemDetailsRequest,BrokerItem brokerItem) {
        ItemContent itemContent = new ItemContent(addBrokerItemDetailsRequest.getShortIntroduction(), addBrokerItemDetailsRequest.getSpecificIntroduction(), brokerItem);
        brokerItemContentRepository.save(itemContent);

        return itemContent;
    }

    @Override
    public List<ItemImage> saveBrokerItemImageDetails(MultipartFile[] brokerItemImg, BrokerItem brokerItem) {
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        ArrayList<ItemImage> itemImages = new ArrayList<>();
        if (!(brokerItemImg == null)) {
            for (MultipartFile multipartFile : brokerItemImg) {
                String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateBrokerItemKeyName(savedUuid), multipartFile);
                ItemImage itemImage = new ItemImage(imageUrl, brokerItem);
                itemImages.add(itemImage);
            }}

        for (ItemImage itemImage : itemImages) {
            brokerItemImageRepository.save(itemImage);
        }
        return itemImages;
    }

    @Override
    public ItemContent updateBrokerItemContent(Long brokerItemId,AddBrokerItemDetailsRequest detailsRequest) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        ItemContent itemContent = brokerItem.getItemContent();
        if (itemContent == null) {
            saveBrokerItemContentDetails(detailsRequest, brokerItem);
        } else {
            itemContent.updateDetails(detailsRequest.getShortIntroduction(), detailsRequest.getSpecificIntroduction());
            brokerItemContentRepository.save(itemContent);
        }
        return itemContent;
    }

    @Override
    @Transactional
    public List<ItemImage> updateBrokerItemImages(Long brokerItemId, MultipartFile[] multipartFiles) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        // 기존 이미지 삭제
        brokerItemImageRepository.deleteByBrokerItem(brokerItem);


        // 새 이미지 업로드 및 ItemImage 엔티티 생성
        List<ItemImage> newImages = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            String uuid = UUID.randomUUID().toString();
            Uuid savedUuid = uuidRepository.save(Uuid.builder().uuid(uuid).build());
            String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateBrokerItemKeyName(savedUuid), multipartFile);
            ItemImage newItemImage = new ItemImage(imageUrl, brokerItem);
            newImages.add(newItemImage);
        }

        // 새로운 이미지 정보 저장
        brokerItemImageRepository.saveAll(newImages);

        // 변경된 이미지 목록 반환
        return newImages;
    }
}

