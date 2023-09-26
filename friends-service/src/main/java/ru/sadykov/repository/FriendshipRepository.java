package ru.sadykov.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.sadykov.entity.Friendship;

import java.util.Optional;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    Optional<Friendship> findByTargetUserAndSourceUserOrSourceUserAndTargetUser(Long targetUser, Long sourceUser,
                                                                                Long sourceUser2, Long targetUser2);
}
