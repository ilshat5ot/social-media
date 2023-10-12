package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class YouAreAFriendDel implements ConditionsForDeletingFriend {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @Override
    public Optional<FriendshipDto> handleARequestToUnfriend(Friendship friendship, Long currentUser) {

        boolean friendshipChange = false;

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.FRIEND) &&
                friendship.getSourceUserId().equals(currentUser) && !friendship.isArchive()) {
            friendship.setRelationshipStatus(RelationshipStatus.SUBSCRIBER);
            friendship.setSourceUserId(friendship.getTargetUserId());
            friendship.setTargetUserId(currentUser);
            friendship.setTimeOfCreation(LocalDateTime.now());
            friendshipChange = true;
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.FRIEND) &&
                friendship.getTargetUserId().equals(currentUser) && !friendship.isArchive()) {
            friendship.setRelationshipStatus(RelationshipStatus.SUBSCRIBER);
            friendship.setTimeOfCreation(LocalDateTime.now());
            friendshipChange = true;
        }
        if (!friendshipChange) {
            return Optional.empty();
        }
        Friendship savedFriendship = friendshipRepository.save(friendship);
        return Optional.of(friendshipMapper.toFriendshipDto(savedFriendship));
    }
}
