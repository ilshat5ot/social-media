package ru.sadykov.service.deletefriend;

import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;

import java.util.Optional;

public interface ConditionsForDeletingFriend {

    Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser);
}
