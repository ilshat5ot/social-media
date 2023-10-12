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
public class YouAreAFriendAdd implements ConditionForAddingAsFriend {

    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> handleFriendRequest(Friendship friendship, Long currentUserId) {

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.FRIEND) && !friendship.isArchive()) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddAsFriendExc());
        }
        return Optional.empty();
    }
}
