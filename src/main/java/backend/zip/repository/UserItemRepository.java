package backend.zip.repository;

import backend.zip.domain.user.User;
import backend.zip.domain.user.UserItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserItemRepository extends JpaRepository<UserItem, Long> {
    // 특정 UserItem을 특정 주소로 업데이트하기
    //@Modifying
    //@Query("UPDATE UserItem u SET u.address = :newAddress WHERE u.userItemId = :userItemId")
    //void updateAddressByUserItemId(@Param("userItemId") Long userItemId, @Param("newAddress") String newAddress);

    // 특정 UserItem을 삭제하는데, User 객체와의 조인 조건 추가
    void deleteByUserId(Long userId);

    // UserItem의 dong을 모두 찾기
    @Query("SELECT u.dong FROM UserItem u")
    List<String> findAllDongs();

    // Fetch Join을 이용해서 option, user를 같이 가져오기
    @Query("SELECT u FROM UserItem u JOIN FETCH u.user JOIN FETCH u.userOption WHERE u.dong = :dongName")
    List<UserItem> findAllByDong(String dongName);

    //@Query("SELECT u FROM UserItem u WHERE u.user.id = :userId")
    // Fetch Join을 이용해서 user를 같이 가져오기
    @Query("SELECT u FROM UserItem u JOIN FETCH u.user WHERE u.user.id = :userId")
    List<UserItem> findByUserId(Long userId);

    UserItem findTopByUserIdAndIsMatchedOrderByCreateAtDesc(Long userId, boolean isMatched);
}
