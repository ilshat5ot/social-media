package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AddConditionHandler {

    private final List<ConditionForAddingAsFriend> conditionForAddingAsFriend;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    public FriendshipDto handleFriendAdditionConditions(Friendship friendship, Long currentUserId) {

        Optional<FriendshipDto> serviceResponse;

        for (ConditionForAddingAsFriend condition : conditionForAddingAsFriend) {
            serviceResponse = condition.handleFriendRequest(friendship, currentUserId);
            if (serviceResponse.isPresent()) {
                return serviceResponse.get();
            }
        }
        throw new AddingAsAFriendException(localizationExceptionMessage.getInvalidRequestExc());
    }
}
