package backend.zip.service;

import backend.zip.domain.enums.MatchStatus;
import backend.zip.domain.match.Matching;
import backend.zip.domain.schedule.Event;
import backend.zip.domain.schedule.Schedule;
import backend.zip.domain.user.UserItem;
import backend.zip.dto.home.response.HomeResponse;
import backend.zip.global.exception.schedule.ScheduleNotFoundException;
import backend.zip.repository.MatchRepository;
import backend.zip.repository.UserItemRepository;
import backend.zip.repository.schedule.EventRepository;
import backend.zip.repository.schedule.ScheduleRepository;
import backend.zip.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static backend.zip.dto.schedule.response.EventResponse.fromEntity;
import static backend.zip.global.status.ErrorStatus.SCHEDULE_NOT_FOUND;

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

        UserItem recentUserItem = userItemRepository.findTopByIsMatchedOrderByCreateAtDesc(false);

        List<Matching> matchingList = null;
        if(recentUserItem != null) {
            matchingList = matchRepository.findByUserItem(recentUserItem);
        }

        int currentMonthValue = currentDate.getMonthValue();

        Schedule schedule = scheduleRepository.findByUserId(userId).orElse(null);

        Event closestEvent = null;
        if(schedule != null) {
            closestEvent = eventRepository.findFirstByScheduleAndEventDateGreaterThanEqualOrderByEventDateAsc(schedule, currentDate);
        }

        return HomeResponse.ShowHomeResponse.builder()
                .recentMatching(recentUserItem == null ? null : HomeResponse.RecentMatchingResponse.builder()
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
