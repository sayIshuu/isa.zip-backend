package backend.zip.service.broker;

import backend.zip.config.AmazonConfig;
import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.item.ItemContent;
import backend.zip.domain.item.ItemImage;
import backend.zip.domain.s3.Uuid;
import backend.zip.dto.brokeritem.request.AddBrokerItemDetailsRequest;
import backend.zip.dto.brokeritem.response.BrokerItemDetailResponse;
import backend.zip.global.aws.s3.AmazonS3Manager;
import backend.zip.repository.UuidRepository;
import backend.zip.repository.broker.BrokerItemContentRepository;
import backend.zip.repository.broker.BrokerItemImageRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    private AmazonConfig amazonConfig;

    @Override
    public ItemContent saveBrokerItemContentDetails(AddBrokerItemDetailsRequest addBrokerItemDetailsRequest,BrokerItem brokerItem) {
        ItemContent itemContent = new ItemContent(addBrokerItemDetailsRequest.getShortIntroduction(),
                addBrokerItemDetailsRequest.getSpecificIntroduction(), brokerItem);
        brokerItemContentRepository.save(itemContent);

        return itemContent;
    }

    @Override
    public List<ItemImage> saveBrokerItemImageDetails(MultipartFile[] brokerItemImg, BrokerItem brokerItem) {
        String uuid = UUID.randomUUID().toString();
        Uuid savedUuid = uuidRepository.save(Uuid.builder()
                .uuid(uuid).build());

        System.out.println("multipartFiles = " +brokerItemImg);

        ArrayList<ItemImage> itemImages = new ArrayList<>();
        if (!(brokerItemImg == null)) {
            for (MultipartFile multipartFile : brokerItemImg) {
                String imageUrl = amazonS3Manager.uploadFile(amazonS3Manager.generateBrokerItemKeyName(savedUuid), multipartFile);
                ItemImage itemImage = new ItemImage(imageUrl,brokerItem);
                itemImages.add(itemImage);
            }
        }

        for (ItemImage itemImage : itemImages) {
            brokerItemImageRepository.save(itemImage);
        }


        return itemImages;
    }
}

