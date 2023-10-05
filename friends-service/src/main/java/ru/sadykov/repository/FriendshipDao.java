package ru.sadykov.repository;

import ru.sadykov.entity.Friendship;

import java.util.Optional;

public interface FriendshipDao {

    Optional<Friendship> findByTargetUserAndSourceUser(Long targetUser, Long sourceUser);
}
