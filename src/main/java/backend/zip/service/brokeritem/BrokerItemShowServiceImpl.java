package backend.zip.service.brokeritem;

import backend.zip.domain.broker.BrokerItem;
import backend.zip.domain.enums.*;
import backend.zip.domain.user.User;
import backend.zip.dto.main.request.CurrentLocationRequest;
import backend.zip.dto.useritem.request.AddUserItemOptionsRequest;
import backend.zip.global.exception.brokeritem.BrokerItemException;
import backend.zip.global.exception.user.UserException;
import backend.zip.global.status.ErrorStatus;
import backend.zip.repository.user.UserRepository;
import backend.zip.repository.broker.BrokerItemByCurrentLocationRepository;
import backend.zip.repository.broker.BrokerItemRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static backend.zip.domain.enums.Role.ROLE_USER;

@Service
@RequiredArgsConstructor
public class BrokerItemShowServiceImpl implements BrokerItemShowService {
    private final BrokerItemRepository brokerItemRepository;
    private final UserRepository userRepository;
    private final BrokerItemByCurrentLocationRepository brokerItemByCurrentLocationRepository;

    @Override
    public List<BrokerItem> findBrokerItemList(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        if (user.getRole() == ROLE_USER) {
            new UserException(ErrorStatus.USER_NOT_AUTHENTICATION_BROKER);
        }

        List<BrokerItem> brokerItemListByUser = brokerItemRepository.findBrokerItemByUser(userId);

        return brokerItemListByUser;
    }

    @Override
    public BrokerItem findBrokerItem(Long brokerItemId) {
        BrokerItem brokerItem = brokerItemRepository.findById(brokerItemId)
                .orElseThrow(() -> new BrokerItemException(ErrorStatus.BROKER_ITEM_NOT_FOUND));

        return brokerItem;
    }

    @Override
    public Long checkBroker() {
        Long userId = Long.valueOf(SecurityUtils.getLoggedInUserId());
        User user = userRepository.findById(userId).get();
        if (user.getRole() == ROLE_USER) {
            throw new UserException(ErrorStatus.USER_NOT_AUTHENTICATION_BROKER);
        }
        return userId;
    }

    @Override
    public Set<String> findDongOfBrokerItem(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorStatus.USER_NOT_FOUND));

        return brokerItemRepository.findBrokerItemByUser(userId).stream()
                .map(brokerItem -> brokerItem.getDong())
                .collect(Collectors.toSet());
    }

    @Override
    public List<BrokerItem> findBrokerItemSortedByDong(String dong) {
        List<BrokerItem> findAllBrokerItemByDong = brokerItemRepository.findAllByDong(dong);
        return findAllBrokerItemByDong;
    }

    @Override
    public List<BrokerItem> findBrokerItemByCurrentLocation(Double x, Double y, AddUserItemOptionsRequest addUserItemOptionsRequest) {
        List<BrokerItem> showBrokerItems = brokerItemByCurrentLocationRepository.findBrokerItemsWithinRadius(x, y);

        Iterator<BrokerItem> iterator = showBrokerItems.iterator();
        // 필터링 아이템 하나하나 보며 유저가 설정한 필터에 걸러지면 제거
        while (iterator.hasNext()) {
            BrokerItem brokerItem = iterator.next();

            if (addUserItemOptionsRequest.getRoomType() != null) {
                if (!addUserItemOptionsRequest.getRoomType().contains(brokerItem.getBrokerOption().getRoomType())) {
                    iterator.remove();
                    continue;
                }
            }
            /* dealType 가격마다 따로 필터링
            if (addUserItemOptionsRequest.getDealTypes() != null) {
                if (!addUserItemOptionsRequest.getDealTypes().contains(brokerItem.getDealType())) {
                    showBrokerItems.remove(brokerItem);
                }
            }*/
            /* roomSize string에서 평방미터 빼내서 비교
            if (addUserItemOptionsRequest.getRoomSize() != null) {
                if (!addUserItemOptionsRequest.getRoomSize().contains(brokerItem.getRoomSize())) {
                    showBrokerItems.remove(brokerItem);
                }
            }*/

            // request의 floor 리스트 요소 중 하나라도 brokerItem의 floor 리스트에 포함되지 않으면 제거
            if (addUserItemOptionsRequest.getFloor() != null) {
                boolean isContain = false;
                for (Floor floor : addUserItemOptionsRequest.getFloor()) {
                    if (brokerItem.getBrokerOption().getBrokerFloors().contains(floor)) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    iterator.remove();
                    continue;
                }
            }

            if (addUserItemOptionsRequest.getManagementOption() != null) {
                boolean isContain = false;
                for (ManagementOption managementOption : addUserItemOptionsRequest.getManagementOption()) {
                    if (brokerItem.getBrokerOption().getBrokerManagementOptions().contains(managementOption)) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    iterator.remove();
                    continue;
                }
            }

            if (addUserItemOptionsRequest.getInternalFacility() != null) {
                boolean isContain = false;
                for (InternalFacility internalFacility : addUserItemOptionsRequest.getInternalFacility()) {
                    if (brokerItem.getBrokerOption().getBrokerInternalFacilities().contains(internalFacility)) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    iterator.remove();
                    continue;
                }
            }
/*
            // approveDate 입력변환 어떻게 됐는지 보고 작성할예정
            if (addUserItemOptionsRequest.getApproveDate() != null) {
                String brokerItemApproveDate = brokerItem.getBrokerOption().getApprovedDate();
                ApproveDate userApproveDate = addUserItemOptionsRequest.getApproveDate();
                //brokerItemApproveDate 앞에서부터 4자리만 잘라서 저장
                Long brokerItemApproveYear = Long.parseLong(brokerItemApproveDate.substring(0, 4));
                //switch문으로 ApproveDate에 따라서 필터링
                switch (userApproveDate) {
                    case UNDER_ONE_YEAR:
                        if (!(brokerItemApproveYear > 2023)) {
                            showBrokerItems.remove(brokerItem);
                            continue;
                        }
                        break;
                    case UNDER_FIVE_YEARS:
                        if (!(brokerItemApproveYear > 2019)) {
                            showBrokerItems.remove(brokerItem);
                            continue;
                        }
                        break;
                    case UNDER_TEN_YEARS:
                        if (!(brokerItemApproveYear > 2014)) {
                            showBrokerItems.remove(brokerItem);
                            continue;
                        }
                        break;
                    case UNDER_FIFTEEN_YEARS:
                        if (!(brokerItemApproveYear > 2009)) {
                            showBrokerItems.remove(brokerItem);
                            continue;
                        }
                        break;
                    case OVER_FIFTEEN_YEARS:
                        if (!(brokerItemApproveYear < 2009)) {
                            showBrokerItems.remove(brokerItem);
                            continue;
                        }
                        break;
                }
            }
*/


            if (addUserItemOptionsRequest.getExtraFilter() != null) {
                boolean isContain = false;
                for (ExtraFilter extraFilter : addUserItemOptionsRequest.getExtraFilter()) {
                    if (brokerItem.getBrokerOption().getBrokerExtraFilters().contains(extraFilter)) {
                        isContain = true;
                        break;
                    }
                }
                if (!isContain) {
                    iterator.remove();
                    continue;
                }
            }
        }
        return showBrokerItems;
    }
}
