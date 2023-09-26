package ru.sadykov.service.add_friend;

import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;

public interface ConditionForAddingAsFriend {

    FriendshipDto processTheTermsOfFriendship(Friendship friendship, Long currentUserId);

    int getCode();
}
