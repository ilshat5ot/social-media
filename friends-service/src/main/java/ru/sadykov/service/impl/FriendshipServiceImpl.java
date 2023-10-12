package ru.sadykov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.exception.exceptions.FriendshipNotFoundException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;
import ru.sadykov.service.FriendshipService;
import ru.sadykov.service.addfriend.AddConditionHandler;
import ru.sadykov.service.deletefriend.DeletionConditionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final AddConditionHandler addConditionHandler;
    private final DeletionConditionHandler deletionConditionHandler;
    private final LocalizationExceptionMessage localizationExceptionMessage;
    private final FriendshipMapper friendshipMapper;

    @Override
    public FriendshipDto addAsFriend(Long currentUserId, Long targetUserId) {

        checkingYourself(currentUserId, targetUserId);

        Optional<Friendship> optionalFriendship = friendshipRepository
                .findByTargetUserAndSourceUser(currentUserId, targetUserId);

        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            return addConditionHandler.handleFriendAdditionConditions(friendship, currentUserId);
        }

        Friendship friendship =
                new Friendship(null, currentUserId, targetUserId,
                        RelationshipStatus.APPLICATION, LocalDateTime.now(), false);

        Friendship savedFriendship = friendshipRepository.save(friendship);
        return friendshipMapper.toFriendshipDto(savedFriendship);
    }

    @Override
    public FriendshipDto deleteFromFriends(Long currentUserId, Long targetUserId) {
        checkingDeleteYourself(currentUserId, targetUserId);

        Friendship friendship = friendshipRepository
                .findByTargetUserAndSourceUser(currentUserId, targetUserId)
                .orElseThrow(() -> new FriendshipNotFoundException(localizationExceptionMessage.getFriendshipNotFoundExc()));

        return deletionConditionHandler.handleFriendRemovalConditions(friendship, currentUserId);
    }

    private void checkingDeleteYourself(Long currentUserId, Long targetId) {
        if (currentUserId.equals(targetId)) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getDeleteYourselfExc());
        }
    }

    private void checkingYourself(Long currentUserId, Long targetId) {
        if (currentUserId.equals(targetId)) {
            throw new AddingAsAFriendException(localizationExceptionMessage.getAddYourselfExc());
        }
    }

}
