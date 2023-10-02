package ru.sadykov.service.addfriend;

import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;

import java.util.Optional;

public interface ConditionForAddingAsFriend {

    Optional<FriendshipDto> processTheTermsOfFriendship(Friendship friendship, Long currentUserId);
}
