//package backend.zip.dto.main.response;
//
//import backend.zip.domain.broker.BrokerItem;
//import backend.zip.domain.enums.ItemStatus;
//import backend.zip.dto.brokeritem.response.BrokerItemAddressResponse;
//import backend.zip.dto.brokeritem.response.BrokerItemDetailResponse;
//import backend.zip.dto.brokeritem.response.BrokerItemOptionResponse;
//import backend.zip.dto.brokeritem.response.ItemImageResponse;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//
//import java.util.List;
//
//@Getter
//@Builder
//@AllArgsConstructor
//public class BrokerItemListResponseOfMain {
//    private Long brokerItemId;
//    private BrokerItemAddressResponse addressResponse;
//    private BrokerItemDetailResponse detailResponse;
//    private BrokerItemOptionResponse optionResponse;
//
//
//    public static BrokerItemListResponseOfMain of(List<BrokerItem> brokerItemByCurrentLocation) {
//        brokerItemByCurrentLocation.stream()
//                .map(brokerItem -> new BrokerItemListResponseOfMain(
//                        brokerItem.getBrokerItemId(),
//
//
//                ))
//    }
//
//}
