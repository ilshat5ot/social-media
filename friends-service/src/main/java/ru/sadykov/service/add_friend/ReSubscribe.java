package ru.sadykov.service.add_friend;

import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.ExceptionConstants;
import ru.sadykov.exception.exceptions.AddAsAFriendException;

@Component
public class ReSubscribe implements ConditionForAddingAsFriend {

    @Override
    public FriendshipDto processTheTermsOfFriendship(Friendship friendship, Long currentUserId) {
        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && currentUserId.equals(friendship.getTargetUser())) {
            throw new AddAsAFriendException(ExceptionConstants.LEFT_AS_A_SUB_EXC);
        }
        return null;
    }

    @Override
    public int getCode() {
        return 4;
    }
}
