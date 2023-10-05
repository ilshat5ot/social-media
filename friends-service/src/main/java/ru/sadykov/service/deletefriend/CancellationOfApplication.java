package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.repository.FriendshipRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@Order(1)
@RequiredArgsConstructor
public class CancellationOfApplication implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;

    @Override
    public Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser) {

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION) &&
                friendship.getSourceUser().equals(currentUser)) {
            friendship.setArchive(true);
            friendship.setTimeOfCreation(LocalDateTime.now().toString());

            Friendship savedFriendship = friendshipRepository.save(friendship);

            return Optional.of(FriendshipDto
                    .builder()
                    .id(savedFriendship.getId())
                    .sourceUserId(savedFriendship.getSourceUser())
                    .targetUserId(savedFriendship.getTargetUser())
                    .relationshipStatus(savedFriendship.getRelationshipStatus())
                    .isArchive(savedFriendship.isArchive())
                    .build());
        }
        return Optional.empty();
    }
}
