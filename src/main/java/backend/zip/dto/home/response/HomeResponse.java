package backend.zip.dto.home.response;

import backend.zip.domain.match.Matching;
import backend.zip.domain.user.UserItem;
import backend.zip.dto.brokeritem.response.BrokerItemNoAddressResponse;
import backend.zip.dto.brokeritem.response.BrokerItemResponse;
import backend.zip.dto.schedule.response.EventResponse;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class HomeResponse {

    @Getter
    public static class ShowHomeResponse {
        private final MatchedItemsResponse matchedItems;
        private final MovingScheduleResponse movingSchedule;

        @Builder
        public ShowHomeResponse(MatchedItemsResponse matchedItems, MovingScheduleResponse movingSchedule) {
            this.matchedItems = matchedItems;
            this.movingSchedule = movingSchedule;
        }
    }

    @Getter
    public static class MovingScheduleResponse {
        private final int month;
        private final EventResponse event;

        @Builder
        public MovingScheduleResponse(int month, EventResponse event) {
            this.month = month;
            this.event = event;
        }
    }

    @Getter
    public static class MatchedItemsResponse {
        private final String dong;
        private final Long matchingCount;
        private final List<BrokerItemNoAddressResponse> matchedBrokerItemResponses;

        @Builder
        public MatchedItemsResponse(UserItem userItem, List<Matching> matchingList) {
            this.dong = userItem.getDong();
            this.matchingCount = (long)matchingList.size();
            this.matchedBrokerItemResponses = matchingList.stream().map(BrokerItemNoAddressResponse::from).collect(Collectors.toList());

        }
    }
}
