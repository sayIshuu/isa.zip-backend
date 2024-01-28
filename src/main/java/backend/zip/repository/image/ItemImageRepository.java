package backend.zip.repository.image;

import backend.zip.domain.item.ItemImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemImageRepository extends JpaRepository<ItemImage, Long> {

}
