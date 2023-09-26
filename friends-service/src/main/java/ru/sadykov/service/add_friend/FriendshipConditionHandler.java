package ru.sadykov.service.add_friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.exception.ExceptionConstants;
import ru.sadykov.exception.exceptions.AddAsAFriendException;

import java.util.List;
import java.util.Map;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class FriendshipConditionHandler {

    private Map<Integer, ConditionForAddingAsFriend> conditionForAddingAsFriend;

    @Autowired
    public FriendshipConditionHandler(List<ConditionForAddingAsFriend> friendshipStatus) {
        this.conditionForAddingAsFriend = friendshipStatus
                .stream()
                .collect(toMap(ConditionForAddingAsFriend::getCode, identity()));
    }

    public FriendshipDto handleFriendshipStatus(Friendship friendship, Long currentUserId) {

        FriendshipDto serviceResponse;

        for (Integer index : conditionForAddingAsFriend.keySet()) {
            serviceResponse = conditionForAddingAsFriend.get(index).processTheTermsOfFriendship(friendship, currentUserId);
            if (serviceResponse != null) {
                return serviceResponse;
            }
        }
        throw new AddAsAFriendException(ExceptionConstants.INVALID_REQUEST_EXC);
    }
}
