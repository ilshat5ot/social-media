package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.repository.FriendshipRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequestForFriendship implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;

    @Override
    public Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser) {

        Friendship savedFriendship = new Friendship();

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION) &&
                friendship.getTargetUser().equals(currentUser) && !friendship.isArchive()) {

            friendship.setRelationshipStatus(RelationshipStatus.SUBSCRIBER);
            friendship.setTimeOfCreation(LocalDateTime.now().toString());

            savedFriendship = friendshipRepository.save(friendship);
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION) &&
                friendship.getSourceUser().equals(currentUser) && !friendship.isArchive()) {
            friendship.setArchive(true);
            friendship.setTimeOfCreation(LocalDateTime.now().toString());

            savedFriendship = friendshipRepository.save(friendship);
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
