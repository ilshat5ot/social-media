package ru.sadykov.service.addfriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RequestForFriendshipAdd implements ConditionForAddingAsFriend {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipMapper friendshipMapper;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> handleFriendRequest(Friendship friendship, Long currentUserId) {

        if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION)
                && !friendship.isArchive()
                && friendship.getTargetUserId().equals(currentUserId)) {

            friendship.setRelationshipStatus(RelationshipStatus.FRIEND);
            friendship.setTimeOfCreation(LocalDateTime.now());
        } else if (friendship.getRelationshipStatus().equals(RelationshipStatus.APPLICATION)

                && !friendship.isArchive()
                && friendship.getSourceUserId().equals(currentUserId)) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddReapplicationExc());
        }
        Friendship savedFriendShip = friendshipRepository.save(friendship);
        return Optional.of(friendshipMapper.toFriendshipDto(savedFriendShip));
    }
}
