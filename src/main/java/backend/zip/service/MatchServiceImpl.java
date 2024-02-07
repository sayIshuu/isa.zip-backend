package backend.zip.service;

import backend.zip.repository.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class MatchServiceImpl {
    private final MatchRepository matchRepository;

    public void matchBrokerItems() {
        // 매칭 후보 등록
    }

    public void unmatchBrokerItems() {
        // 매칭 후보 삭제
    }

    public void matchCompleteBrokerItems() {
        // 매칭 완료
    }

    public void matchUserItems() {
        // 매칭 전체 조회
    }
}
