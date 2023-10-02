package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.repository.FriendshipRepository;

import java.util.Optional;

@Component
@Order(3)
@RequiredArgsConstructor
public class AddingAsFriend implements ConditionForAddingAsFriend {

    private final FriendshipRepository friendRepository;

    @Override
    public Optional<FriendshipDto> processTheTermsOfFriendship(Friendship friendship, Long currentUserId) {
        if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION)
                && currentUserId.equals(friendship.getTargetUser())) {

            friendship.setRelationshipStatus(RelationshipStatus.FRIEND);
            Friendship savedFriendship = friendRepository.save(friendship);

            return Optional.of(FriendshipDto
                    .builder()
                    .id(savedFriendship.getId())
                    .sourceUserId(savedFriendship.getSourceUser())
                    .targetUserId(savedFriendship.getTargetUser())
                    .relationshipStatus(savedFriendship.getRelationshipStatus())
                    .build());
        }
        return Optional.empty();
    }
}
