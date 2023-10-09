package ru.sadykov.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.AddingAsAFriendException;
import ru.sadykov.exception.exceptions.FriendshipNotFoundException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.repository.FriendshipRepository;
import ru.sadykov.service.FriendshipService;
import ru.sadykov.service.addfriend.FriendshipConditionHandler;
import ru.sadykov.service.deletefriend.DeletionConditionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final FriendshipConditionHandler friendshipConditionHandler;
    private final DeletionConditionHandler deletionConditionHandler;
    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public FriendshipDto addAsFriend(Long currentUserId, Long userId) {

        checkingYourself(currentUserId, userId);

        Optional<Friendship> optionalFriendship = friendshipRepository
                .findByTargetUserAndSourceUser(currentUserId, userId);

        if (optionalFriendship.isPresent()) {
            Friendship friendship = optionalFriendship.get();
            if (friendship.isArchive()) {
                friendship.setRelationshipStatus(RelationshipStatus.APPLICATION);
                Friendship savedFriendship = friendshipRepository.save(friendship);
                return FriendshipDto
                        .builder()
                        .id(savedFriendship.getId())
                        .sourceUserId(savedFriendship.getSourceUser())
                        .targetUserId(savedFriendship.getTargetUser())
                        .relationshipStatus(savedFriendship.getRelationshipStatus())
                        .build();
            } else {
                return friendshipConditionHandler.handleFriendshipStatus(friendship, currentUserId);
            }
        } else {
            Friendship newFriendship = fillFriend(currentUserId, userId, RelationshipStatus.APPLICATION, LocalDateTime.now(), false);
            Friendship savedFriendship = friendshipRepository.save(newFriendship);
            return FriendshipDto
                    .builder()
                    .id(savedFriendship.getId())
                    .sourceUserId(savedFriendship.getSourceUser())
                    .targetUserId(savedFriendship.getTargetUser())
                    .relationshipStatus(savedFriendship.getRelationshipStatus())
                    .build();
        }
    }

    @Override
    public FriendshipDto deleteFromFriends(Long currentUserId, Long targetId) {
        checkingDeleteYourself(currentUserId, targetId);

        Friendship friendship = friendshipRepository
                .findByTargetUserAndSourceUser(currentUserId, targetId)
                .orElseThrow(() -> new FriendshipNotFoundException(localizationExceptionMessage.getFriendshipNotFoundExc()));

        return deletionConditionHandler.handleConditionsForDeletingFriending(friendship, currentUserId);
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

    private Friendship fillFriend(Long currentUserId, Long userId, RelationshipStatus friendApplication,
                                  LocalDateTime timeOfCreation, boolean isArchive) {
        Friendship friend = new Friendship();
        friend.setTargetUser(userId);
        friend.setSourceUser(currentUserId);
        friend.setRelationshipStatus(friendApplication);
        friend.setTimeOfCreation(timeOfCreation.toString());
        friend.setArchive(isArchive);
        return friend;
    }
}
