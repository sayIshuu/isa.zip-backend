package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.broker.BrokerOption;
import backend.zip.domain.broker.options.*;
import backend.zip.domain.enums.DealType;
import backend.zip.domain.enums.ExtraFilter;
import backend.zip.domain.enums.InternalFacility;
import backend.zip.domain.enums.ManagementOption;
import backend.zip.dto.brokeritem.request.AddBrokerItemOptionsRequest;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.broker.option.*;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.repository.broker.BrokerOptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BrokerItemOptionServiceImpl implements BrokerItemOptionService {
    private final BrokerItemRepository brokerItemRepository;
    private final BrokerOptionRepository brokerOptionRepository;
    private final BrokerDealTypeRepository brokerDealTypeRepository;
    private final BrokerManagementOptionRepository brokerManagementOptionRepository;
    private final BrokerFloorRepository brokerFloorRepository;
    private final BrokerInternalFacilityRepository brokerInternalFacilityRepository;
    private final BrokerExtraFilterRepository brokerExtraFilterRepository;

    @Override
    public BrokerOption saveBrokerItemOptions(AddBrokerItemOptionsRequest addBrokerItemOptionsRequest) {
        BrokerOption brokerOption = createBrokerOption(addBrokerItemOptionsRequest);
        return brokerOptionRepository.save(brokerOption);
    }

    public BrokerOption createBrokerOption(AddBrokerItemOptionsRequest optionsRequest) {
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

    public void setBrokerOptionReference(AddBrokerItemOptionsRequest optionsRequest, BrokerOption brokerOption) {
        //DealType 엔티티들에 BrokerOption 참조 설정
        optionsRequest.getDealTypes().forEach(dealType -> {
            AddBrokerItemOptionsRequest.DealInfo dealInfo = optionsRequest.getDealInfoMap().get(dealType);
            BrokerDealType brokerDealType = BrokerDealType.builder()
                    .dealType(dealType)
                    .charterPrice(dealInfo.getCharterPrice())
                    .tradingPrice(dealInfo.getTradingPrice())
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


    @Transactional
    @Override
    public BrokerOption updateBrokerItemOptions(Long brokerItemId, AddBrokerItemOptionsRequest optionsRequest) {
        // 1. 기존 옵션 조회
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));
        BrokerOption brokerOption = brokerItem.getBrokerOption();

        brokerDealTypeRepository.deleteByBrokerOption(brokerOption);
        brokerManagementOptionRepository.deleteByBrokerOption(brokerOption);
        brokerFloorRepository.deleteByBrokerOption(brokerOption);
        brokerInternalFacilityRepository.deleteByBrokerOption(brokerOption);
        brokerExtraFilterRepository.deleteByBrokerOption(brokerOption);


        brokerOption.setRoomType(optionsRequest.getRoomType());
        brokerOption.setRoomSize(optionsRequest.getRoomSize());
        brokerOption.setApprovedDate(optionsRequest.getApproveDate());

        // DealType 업데이트
        brokerOption.getBrokerDealTypes().clear(); // 기존 거래 유형을 모두 제거
        for (DealType dealType : optionsRequest.getDealTypes()) {
            AddBrokerItemOptionsRequest.DealInfo dealInfo = optionsRequest.getDealInfoMap().get(dealType);

            BrokerDealType brokerDealType = BrokerDealType.builder()
                    .dealType(dealType)
                    .charterPrice(dealInfo.getCharterPrice())
                    .tradingPrice(dealInfo.getTradingPrice())
                    .monthPrice(dealInfo.getMonthPrice())
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerDealTypes().add(brokerDealType);
        }

        // ManagementOption 업데이트
        brokerOption.getBrokerManagementOptions().clear(); // 기존 관리 옵션을 모두 제거
        for (ManagementOption managementOption : optionsRequest.getManagementOptions()) {
            BrokerManagementOption management = BrokerManagementOption.builder()
                    .managementOption(managementOption)
                    .managementPrice(optionsRequest.getManagementPrice())
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerManagementOptions().add(management);
        }

        // Floor 업데이트
        brokerOption.getBrokerFloors().clear(); // 기존 층 정보를 모두 제거
        // 선택된 층 정보가 있으면 업데이트
        optionsRequest.getSelectedFloor().forEach(floor -> {
            BrokerFloor brokerFloor = BrokerFloor.builder()
                    .floor(floor)
                    .brokerOption(brokerOption)
                    .build();
            brokerOption.getBrokerFloors().add(brokerFloor);
        });
        // 사용자 지정 층 정보가 있으면 업데이트
        if (optionsRequest.getCustomFloor() != null) {
            BrokerFloor brokerFloor = BrokerFloor.builder()
                    .customFloor(optionsRequest.getCustomFloor())
                    .brokerOption(brokerOption)
                    .build();
            brokerOption.getBrokerFloors().add(brokerFloor);
        }

        // InternalFacility 업데이트
        brokerOption.getBrokerInternalFacilities().clear(); // 기존 내부 시설 정보를 모두 제거
        for (InternalFacility internalFacility : optionsRequest.getInternalFacilities()) {
            BrokerInternalFacility facility = BrokerInternalFacility.builder()
                    .internalFacility(internalFacility)
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerInternalFacilities().add(facility);
        }

        // ExtraFilter 업데이트
        brokerOption.getBrokerExtraFilters().clear(); // 기존 추가 필터 정보를 모두 제거
        for (ExtraFilter extraFilter : optionsRequest.getExtraFilters()) {
            BrokerExtraFilter filter = BrokerExtraFilter.builder()
                    .extraFilter(extraFilter)
                    .brokerOption(brokerOption) // 참조 설정
                    .build();
            brokerOption.getBrokerExtraFilters().add(filter);
        }
        brokerOptionRepository.save(brokerOption);
        return brokerOption;
    }
}
