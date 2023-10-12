package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YouAreASubscriberAdd implements ConditionForAddingAsFriend {

    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> handleFriendRequest(Friendship friendship, Long currentUserId) {
        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER) && !friendship.isArchive() &&
                friendship.getSourceUserId().equals(currentUserId)) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddAreYouASubExc());
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER) && !friendship.isArchive() &&
                friendship.getTargetUserId().equals(currentUserId)) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddLeaveInSubExc());
        }
        return Optional.empty();
    }
}
