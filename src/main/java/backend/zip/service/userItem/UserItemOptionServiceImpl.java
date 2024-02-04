package backend.zip.service.useritem;

import backend.zip.domain.enums.ApproveDate;
import backend.zip.domain.user.UserOption;
import backend.zip.domain.user.options.*;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.repository.UserOptionRepository;
import backend.zip.service.userItem.UserItemOptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserItemOptionServiceImpl implements UserItemOptionService {

    private final UserOptionRepository userItemOptionRepository;

    @Override
    public UserOption saveUserItemOptions(AddUserItemOptionsRequest addUserItemOptionsRequest) {
        UserOption userOption = createUserOption(addUserItemOptionsRequest);

        return userItemOptionRepository.save(userOption);
    }

    private UserOption createUserOption(AddUserItemOptionsRequest optionsRequest) {
        // RoomType 생성 로직
        List<UserRoomType> roomTypes = optionsRequest.getRoomType().stream()
                .map(roomType -> UserRoomType.builder()
                        .roomType(roomType)
                        .build())
                .collect(Collectors.toList());

        // DealType 생성 로직
        List<UserDealType> dealTypes = optionsRequest.getDealTypes().stream()
                .map(dealType -> {
                    AddUserItemOptionsRequest.DealTypeInfo dealTypeInfo = optionsRequest.getDealInfoMap().get(dealType);

                    // DealType에 속한 DealTypeInfo 생성
                    return UserDealType.builder()
                            .dealType(dealType)
                            .minPrice(dealTypeInfo.getMinPrice())
                            .maxPrice(dealTypeInfo.getMaxPrice())
                            .minDeposit(dealTypeInfo.getMinDeposit())
                            .maxDeposit(dealTypeInfo.getMaxDeposit())
                            .minMonthPrice(dealTypeInfo.getMinMonthPrice())
                            .maxMonthPrice(dealTypeInfo.getMaxMonthPrice())
                            .build();

                }).collect(Collectors.toList());

        // RoomSize 생성 로직
        List<UserRoomSize> roomSizes = optionsRequest.getRoomSize().stream()
                .map(roomSize -> UserRoomSize.builder()
                        .roomSize(roomSize)
                        .build())
                .collect(Collectors.toList());

        // Floor 생성 로직
        List<UserFloor> floors = optionsRequest.getFloor().stream()
                .map(floor -> UserFloor.builder()
                        .floor(floor)
                        .build())
                .collect(Collectors.toList());

        // ManagementOption 생성 로직
        List<UserManagementOption> managementOptions = optionsRequest.getManagementOption().stream()
                .map(managementOption ->
                        UserManagementOption.builder()
                                .managementOption(managementOption)
                                .build()).collect(Collectors.toList());

        // InternalFacility 생성 로직
        List<UserInternalFacility> internalFacilities = optionsRequest.getInternalFacility().stream()
                .map(internalFacility ->
                        UserInternalFacility.builder()
                                .internalFacility(internalFacility)
                                .build()).collect(Collectors.toList());

        // ExtraFilter 생성 로직
        List<UserExtraFilter> extraFilters = optionsRequest.getExtraFilter().stream()
                .map(extraFilter ->
                        UserExtraFilter.builder()
                                .extraFilter(extraFilter)
                                .build()).collect(Collectors.toList());

        // UserOption 완성 로직
        UserOption userOption = UserOption.builder()
                .userRoomTypes(roomTypes)
                .userDealTypes(dealTypes)
                .userRoomSizes(roomSizes)
                .userFloors(floors)
                .userManagementOptions(managementOptions)
                .userInternalFacilities(internalFacilities)
                .approveDate(optionsRequest.getApproveDate())
                .userExtraFilters(extraFilters)
                .build();

        // 참조 설정을 여기에서 수행 ( 잘이해안됨 이방법 먼지.. 근데 일단 응답보셨고, 옵션저장로직 그대로 차용해서 일단적용했습니다. )
        setUserOptionReference(optionsRequest, userOption);

        return userOption;
    }


    private void setUserOptionReference(AddUserItemOptionsRequest optionsRequest, UserOption userOption) {
        // UserRoomType의 참조 설정
        optionsRequest.getRoomType().forEach(roomType -> {
            UserRoomType userRoomType = UserRoomType.builder()
                    .roomType(roomType)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserRoomTypes().add(userRoomType);
        });

        // UserDealType의 참조 설정
        optionsRequest.getDealTypes().forEach(dealType -> {
            AddUserItemOptionsRequest.DealTypeInfo dealTypeInfo = optionsRequest.getDealInfoMap().get(dealType);
            UserDealType userDealType = UserDealType.builder()
                    .dealType(dealType)
                    .minPrice(dealTypeInfo.getMinPrice())
                    .maxPrice(dealTypeInfo.getMaxPrice())
                    .minDeposit(dealTypeInfo.getMinDeposit())
                    .maxDeposit(dealTypeInfo.getMaxDeposit())
                    .minMonthPrice(dealTypeInfo.getMinMonthPrice())
                    .maxMonthPrice(dealTypeInfo.getMaxMonthPrice())
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserDealTypes().add(userDealType);
        });

        // UserRoomSize의 참조 설정
        optionsRequest.getRoomSize().forEach(roomSize -> {
            UserRoomSize userRoomSize = UserRoomSize.builder()
                    .roomSize(roomSize)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserRoomSizes().add(userRoomSize);
        });

        // UserFloor의 참조 설정
        optionsRequest.getFloor().forEach(floor -> {
            UserFloor userFloor = UserFloor.builder()
                    .floor(floor)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserFloors().add(userFloor);
        });

        // UserManagementOption의 참조 설정
        optionsRequest.getManagementOption().forEach(managementOption -> {
            UserManagementOption userManagementOption = UserManagementOption.builder()
                    .managementOption(managementOption)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserManagementOptions().add(userManagementOption);
        });


        // UserInternalFacility의 참조 설정
        optionsRequest.getInternalFacility().forEach(internalFacility -> {
            UserInternalFacility userInternalFacility = UserInternalFacility.builder()
                    .internalFacility(internalFacility)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserInternalFacilities().add(userInternalFacility);
        });

        // UserExtraFilter의 참조 설정
        optionsRequest.getExtraFilter().forEach(extraFilter -> {
            UserExtraFilter userExtraFilter = UserExtraFilter.builder()
                    .extraFilter(extraFilter)
                    .userOption(userOption) // 참조설정
                    .build();
            userOption.getUserExtraFilters().add(userExtraFilter);
        });
    }
}
