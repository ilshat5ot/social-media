package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.Optional;

@Component
@Order(4)
@RequiredArgsConstructor
public class ReSubscribe implements ConditionForAddingAsFriend {

    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> processTheTermsOfFriendship(Friendship friendship, Long currentUserId) {
        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && currentUserId.equals(friendship.getTargetUser())) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddLeaveInSubExc());
        }
        return Optional.empty();
    }
}
