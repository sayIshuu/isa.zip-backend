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

    // 특정 주소(address)를 가진 UserItem 목록 찾기
    List<UserItem> findByAddress(String address);

    // 특정 User에 속한 UserItem 목록 찾기
    List<UserItem> findByUserId(Long userId);

    // 특정 User에 속한 UserItem 중에서 특정 주소(address)를 가진 것 찾기
    Optional<UserItem> findByUserAndAddress(User user, String address);

    // 특정 UserItem을 특정 주소로 업데이트하기
    @Modifying
    @Query("UPDATE UserItem u SET u.address = :newAddress WHERE u.userItemId = :userItemId")
    void updateAddressByUserItemId(@Param("userItemId") Long userItemId, @Param("newAddress") String newAddress);

    long countByAddress(String address);

    // 특정 UserItem을 삭제하는데, User 객체와의 조인 조건 추가
    void deleteByUserId(Long userId);
}
