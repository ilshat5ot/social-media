package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.DeleteUserFromFriendsException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.repository.FriendshipRepository;

import java.util.Optional;

@Component
@Order(3)
@RequiredArgsConstructor
public class YouAreASubscriber implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser) {

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getSourceUser().equals(currentUser)) {
            friendship.setArchive(true);

            Friendship savedFriendship = friendshipRepository.save(friendship);

            return Optional.of(FriendshipDto
                    .builder()
                    .id(savedFriendship.getId())
                    .sourceUserId(savedFriendship.getSourceUser())
                    .targetUserId(savedFriendship.getTargetUser())
                    .relationshipStatus(savedFriendship.getRelationshipStatus())
                    .isArchive(savedFriendship.isArchive())
                    .build());
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getTargetUser().equals(currentUser)) {
            throw new DeleteUserFromFriendsException(localizationExceptionMessage.getDeleteUserAreSubExc());
        }
        return Optional.empty();
    }
}
