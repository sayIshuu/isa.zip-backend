package backend.zip.service.broker;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.*;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.broker.BrokerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrokerItemOptionServiceImpl implements BrokerItemOptionService {
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerOptionRepository brokerOptionRepository;

    @Override
    public BrokerOption saveBrokerItemOptions(AddBrokerItemOptionsRequest addBrokerItemOptionsRequest) {
        BrokerOption brokerOption = createBrokerOption(addBrokerItemOptionsRequest);
        brokerOption = brokerOptionRepository.save(brokerOption);

        return brokerOption;
    }

    private BrokerOption createBrokerOption(AddBrokerItemOptionsRequest optionsRequest) {

        // DealType과 관련된 옵션 엔티티 생성 로직
        List<BrokerDealType> dealTypes = optionsRequest.getDealTypes().stream()
                .map(dealType -> {
                    AddBrokerItemOptionsRequest.DealInfo dealInfo = optionsRequest.getDealInfoMap().get(dealType);

                    return BrokerDealType.builder()
                            .dealType(dealType)
                            .price(dealInfo.getPrice())
                            .deposit(dealInfo.getDeposit())
                            .monthPrice(dealInfo.getMonthPrice())
                            .build();

                }).collect(Collectors.toList());


        // Floor 리스트 생성 로직
//        List<BrokerFloor> brokerFloors = new ArrayList<>();
        if (optionsRequest.getSelectedFloor() != null) {
            List<BrokerFloor> floors = optionsRequest.getSelectedFloor().stream()
                    .map(floor -> BrokerFloor.builder()
                            .floor(floor)
                            .build())
                    .collect(Collectors.toList());
        }
        if (optionsRequest.getCustomFloor() != null && !optionsRequest.getCustomFloor().isEmpty()) {
            BrokerFloor floor = BrokerFloor.builder()
                    .customFloor(optionsRequest.getCustomFloor())
                    .build();
        }


        // ManagementOption 리스트 생성 로직
        List<BrokerManagementOption> managementOptionEntities = optionsRequest.getManagementOptions().stream()
                .map(managementOption ->
                        BrokerManagementOption.builder()
                                .managementOption(managementOption)
                                .managementPrice(optionsRequest.getManagementPrice()) // 이 부분에 적절한 값을 설정
                                .build()).collect(Collectors.toList());


        // InternalFacility 리스트 생성 로직
        List<BrokerInternalFacility> internalFacilityEntities = optionsRequest.getInternalFacilities().stream()
                .map(internalFacility ->
                        BrokerInternalFacility.builder()
                                .internalFacility(internalFacility)
                                .build()).collect(Collectors.toList());


        // ExtraFilter 리스트 생성 로직
        List<BrokerExtraFilter> extraFilterEntities = optionsRequest.getExtraFilters().stream()
                .map(extraFilter ->
                        BrokerExtraFilter.builder()
                                .extraFilter(extraFilter)
                                .build()).collect(Collectors.toList());


        // BrokerOption 생성 로직
        BrokerOption brokerOption = BrokerOption.builder()
                .roomType(optionsRequest.getRoomType())
                .brokerDealTypes(new ArrayList<>())
                .roomSize(optionsRequest.getRoomSize())
                .brokerFloors(new ArrayList<>())
                .brokerManagementOptions(new ArrayList<>())
                .brokerInternalFacilities(new ArrayList<>())
                .approvedDate(optionsRequest.getApproveDate())
                .brokerExtraFilters(new ArrayList<>())
                .build();

        // 참조 설정을 여기에서 수행
        setBrokerOptionReference(optionsRequest, brokerOption);

        return brokerOption;
    }

    private void setBrokerOptionReference(AddBrokerItemOptionsRequest optionsRequest, BrokerOption brokerOption) {
        //DealType 엔티티들에 BrokerOption 참조 설정
        optionsRequest.getDealTypes().forEach(dealType -> {
            AddBrokerItemOptionsRequest.DealInfo dealInfo = optionsRequest.getDealInfoMap().get(dealType);
            BrokerDealType brokerDealType = BrokerDealType.builder()
                    .dealType(dealType)
                    .price(dealInfo.getPrice())
                    .deposit(dealInfo.getDeposit())
                    .monthPrice(dealInfo.getMonthPrice())
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerDealTypes().add(brokerDealType);
        });

        // ManagementOption 엔티티들에 BrokerOption 참조 설정
        optionsRequest.getManagementOptions().forEach(managementOption -> {
            BrokerManagementOption management = BrokerManagementOption.builder()
                    .managementOption(managementOption)
                    .managementPrice(optionsRequest.getManagementPrice())
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerManagementOptions().add(management);
        });

        System.out.println("optionsRequest = " + optionsRequest.getCustomFloor());
        System.out.println("optionsRequest = " + optionsRequest.getSelectedFloor());
        // Floor 엔티티들에 BrokerOption 참조 설정
        if (optionsRequest.getCustomFloor()==null) {
            optionsRequest.getSelectedFloor().forEach(floor -> {
                BrokerFloor brokerFloor = BrokerFloor.builder()
                        .floor(floor)
                        .brokerOption(brokerOption)
                        .build();
                brokerOption.getBrokerFloors().add(brokerFloor);
            });
        }
        if (optionsRequest.getSelectedFloor()==null) {
            String customFloor = optionsRequest.getCustomFloor();
            BrokerFloor brokerFloor = BrokerFloor.builder()
                    .customFloor(customFloor)
                    .brokerOption(brokerOption)
                    .build();
            brokerOption.getBrokerFloors().add(brokerFloor);
        }

        // InternalFacility 엔티티들에 BrokerOption 참조 설정
        optionsRequest.getInternalFacilities().forEach(internalFacility -> {
            BrokerInternalFacility facility = BrokerInternalFacility.builder()
                    .internalFacility(internalFacility)
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerInternalFacilities().add(facility);
        });

        // ExtraFilter 엔티티들에 BrokerOption 참조 설정
        optionsRequest.getExtraFilters().forEach(extraFilter -> {
            BrokerExtraFilter filter = BrokerExtraFilter.builder()
                    .extraFilter(extraFilter)
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerExtraFilters().add(filter);
        });
    }

}
