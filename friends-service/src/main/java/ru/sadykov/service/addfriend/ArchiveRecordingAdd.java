package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArchiveRecordingAdd implements ConditionForAddingAsFriend {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;

    @Override
    public Optional<FriendshipDto> handleFriendRequest(Friendship friendship, Long currentUserId) {
        if (friendship.isArchive()) {
            friendship.setRelationshipStatus(RelationshipStatus.APPLICATION);
            friendship.setArchive(true);
            Friendship savedFriendship = friendshipRepository.save(friendship);
            return Optional.of(friendshipMapper.toFriendshipDto(savedFriendship));
        }
        return Optional.empty();
    }
}
