package backend.zip.global.aws.s3;

import backend.zip.Repository.UuidRepository;
import backend.zip.config.AmazonConfig;
import backend.zip.domain.s3.Uuid;
import com.amazonaws.services.s3.AmazonS3;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Manager{

    private final AmazonS3 amazonS3;

    private final AmazonConfig amazonConfig;

    private final UuidRepository uuidRepository;

    public String uploadFile(String keyName, MultipartFile file){ return null;}

    public String generateUserKeyName(Uuid uuid) {
        return amazonConfig.getUserPath() + '/' + uuid.getUuid();
    }

    public String generateItemKeyName(Uuid uuid) {
        return amazonConfig.getItemPath() + '/' + uuid.getUuid();
    }

    public String generateBrokerKeyName(Uuid uuid) {
        return amazonConfig.getBrokerPath() + '/' + uuid.getUuid();
    }
}