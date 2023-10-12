package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.UnfriendingException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YouAreASubscriberDel implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;
    private final LocalizationExceptionMessage localizationExceptionMessage;
    private final FriendshipMapper friendshipMapper;

    @Override
    public Optional<FriendshipDto> handleARequestToUnfriend(Friendship friendship, Long currentUser) {

        boolean friendshipChange = false;

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getSourceUserId().equals(currentUser) && !friendship.isArchive()) {
            friendship.setArchive(true);
            friendshipChange = true;
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getTargetUserId().equals(currentUser) && !friendship.isArchive()) {
            throw new UnfriendingException(localizationExceptionMessage.getDeleteUserAreSubExc());
        }
        if (!friendshipChange) {
            return Optional.empty();
        }
        Friendship savedFriendship = friendshipRepository.save(friendship);
        return Optional.of(friendshipMapper.toFriendshipDto(savedFriendship));
    }
}
