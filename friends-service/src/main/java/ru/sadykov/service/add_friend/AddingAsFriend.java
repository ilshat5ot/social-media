package ru.sadykov.service.add_friend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.repository.FriendshipRepository;

@Component
@RequiredArgsConstructor
public class AddingAsFriend implements ConditionForAddingAsFriend {

    private final FriendshipRepository friendRepository;

    @Override
    public FriendshipDto processTheTermsOfFriendship(Friendship friendship, Long currentUserId) {
        if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION)
                && currentUserId.equals(friendship.getTargetUser())) {

            friendship.setRelationshipStatus(RelationshipStatus.FRIEND);
            Friendship savedFriendship = friendRepository.save(friendship);

            return FriendshipDto
                    .builder()
                    .id(savedFriendship.getId())
                    .sourceUserId(savedFriendship.getSourceUser())
                    .targetUserId(savedFriendship.getTargetUser())
                    .relationshipStatus(savedFriendship.getRelationshipStatus())
                    .build();
        }
        return null;
    }

    @Override
    public int getCode() {
        return 3;
    }
}
