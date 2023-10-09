package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.UnfriendingException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.repository.FriendshipRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YouAreASubscriber implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser) {

        Friendship savedFriendship = new Friendship();

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getSourceUser().equals(currentUser) && !friendship.isArchive()) {
            friendship.setArchive(true);

            savedFriendship = friendshipRepository.save(friendship);
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.SUBSCRIBER)
                && friendship.getTargetUser().equals(currentUser) && !friendship.isArchive()) {
            throw new UnfriendingException(localizationExceptionMessage.getDeleteFriendExc());
        }
        if (savedFriendship.getId() == null) {
            return Optional.empty();
        }
        return Optional.of(FriendshipDto
                .builder()
                .id(savedFriendship.getId())
                .sourceUserId(savedFriendship.getSourceUser())
                .targetUserId(savedFriendship.getTargetUser())
                .relationshipStatus(savedFriendship.getRelationshipStatus())
                .isArchive(savedFriendship.isArchive())
                .build());
    }
}
