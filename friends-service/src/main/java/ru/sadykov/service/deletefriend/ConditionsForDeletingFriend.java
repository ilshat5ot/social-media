package ru.sadykov.service.deletefriend;

import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;

import java.util.Optional;

public interface ConditionsForDeletingFriend {

    Optional<FriendshipDto> handleARequestToUnfriend(Friendship friendship, Long currentUser);
}
