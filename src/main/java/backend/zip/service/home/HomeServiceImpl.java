package backend.zip.service.home;

import backend.zip.domain.match.Matching;
import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.UserItem;
import backend.zip.dto.home.response.HomeResponse;
import backend.zip.repository.MatchRepository;
import backend.zip.repository.UserItemRepository;
import backend.zip.repository.schedule.EventRepository;
import backend.zip.repository.schedule.ScheduleRepository;
import backend.zip.security.SecurityUtils;
import backend.zip.service.home.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static backend.zip.dto.schedule.response.EventResponse.fromEntity;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
    private final ScheduleRepository scheduleRepository;
    private final EventRepository eventRepository;
    private final UserItemRepository userItemRepository;
    private final MatchRepository matchRepository;
    @Override
    public HomeResponse.ShowHomeResponse showHome() {
        Long userId = Long.parseLong(SecurityUtils.getLoggedInUserId());
        LocalDate currentDate = LocalDate.now();

        // 최신 유저 요청 조회
        UserItem recentUserItem = userItemRepository.findTopByUserIdAndIsMatchedOrderByCreateAtDesc(userId, false);

        // 유저 요청과 매칭된 매물들 조회
        List<Matching> matchingList = null;
        if(recentUserItem != null) {
            matchingList = matchRepository.findByUserItem(recentUserItem);
        }

        // 현재 달 조회
        int currentMonthValue = currentDate.getMonthValue();

        // 일정을 등록한 경우, 현재 날짜와 가장 가까운 상세 일정 조회
        Schedule schedule = scheduleRepository.findByUserId(userId).orElse(null);

        Event closestEvent = null;
        if(schedule != null) {
            closestEvent = eventRepository.findFirstByScheduleAndEventDateGreaterThanEqualOrderByEventDateAsc(schedule, currentDate);
        }

        return HomeResponse.ShowHomeResponse.builder()
                .matchedItems(recentUserItem == null ? null : HomeResponse.MatchedItemsResponse.builder()
                        .userItem(recentUserItem)
                        .matchingList(matchingList)
                        .build())
                .movingSchedule(closestEvent == null ? null : HomeResponse.MovingScheduleResponse.builder()
                        .month(currentMonthValue)
                        .event(fromEntity(closestEvent))
                        .build())
                .build();
    }
}
