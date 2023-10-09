package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.exception.exceptions.InvalidRequestException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeletionConditionHandler {

    private final List<ConditionsForDeletingFriend> conditionsForDeletingFriends;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    public FriendshipDto handleConditionsForDeletingFriending(Friendship friendship, Long currentUserId) {

        return conditionsForDeletingFriends.stream()
                .map(conditions -> conditions.processTheTermsOfDeletingFromFriends(friendship, currentUserId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException(localizationExceptionMessage.getInvalidRequestExc()));
    }
}
