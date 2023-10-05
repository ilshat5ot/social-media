package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.exception.exceptions.DeleteUserFromFriendsException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DeletionConditionHandler {

    private final List<ConditionsForDeletingFriend> conditionsForDeletingFriends;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    public FriendshipDto handleConditionsForDeletingFriending(Friendship friendship, Long currentUserId) {

        Optional<FriendshipDto> response;

        for (ConditionsForDeletingFriend conditions : conditionsForDeletingFriends) {
            response = conditions.processTheTermsOfDeletingFromFriends(friendship, currentUserId);

            if (response.isPresent()) {
                return response.get();
            }
        }
        throw new DeleteUserFromFriendsException(localizationExceptionMessage.getInvalidRequestExc());
    }
}
