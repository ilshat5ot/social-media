package ru.sadykov.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.repository.FriendshipRepository;
import ru.sadykov.service.deletefriend.DeletionConditionHandler;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {

    @InjectMocks
    FriendshipServiceImpl friendshipService;

    @Mock
    FriendshipRepository friendshipRepository;

    @Mock
    DeletionConditionHandler deletionConditionHandler;

    private static Friendship statusFriend;
    private static Friendship statusApplication;
    private static Friendship statusSubscriber;
    private static Friendship friendshipIsArchive;

    private static Long sourceUserId = 1L;
    private static Long targetUserId = 2L;

    @BeforeEach
    void prepareDate() {
        statusFriend = new Friendship(1L, 1L, 2L,
                RelationshipStatus.FRIEND, LocalDateTime.now().toString(), false);

        statusApplication = new Friendship(1L, 1L, 2L,
                RelationshipStatus.APPLICATION, LocalDateTime.now().toString(), false);

        statusSubscriber = new Friendship(1L, 1L, 2L,
                RelationshipStatus.SUBSCRIBER, LocalDateTime.now().toString(), false);

        friendshipIsArchive = new Friendship(1L, 1L, 2L,
                RelationshipStatus.APPLICATION, LocalDateTime.now().toString(), true);
    }

    @Test
    @DisplayName("Проверка удаления из друзей один")
    void testDeleteFromFriendsOne_Success() {
        // Arrange
        when(friendshipRepository.findByTargetUserAndSourceUser(any(), any())).thenReturn(Optional.of(statusFriend));

        FriendshipDto expectedDto = FriendshipDto.builder()
                .id(statusFriend.getId())
                .relationshipStatus(RelationshipStatus.SUBSCRIBER)
                .sourceUserId(statusFriend.getSourceUser())
                .targetUserId(statusFriend.getTargetUser())
                .isArchive(statusFriend.isArchive())
                .build();

        when(deletionConditionHandler.handleConditionsForDeletingFriending(any(), any())).thenReturn(expectedDto);

        // Act
        FriendshipDto result = friendshipService.deleteFromFriends(sourceUserId, targetUserId);

        // Assert
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.isArchive()).isFalse(),
                () -> assertThat(result.sourceUserId()).isEqualTo(sourceUserId),
                () -> assertThat(result.relationshipStatus()).isEqualTo(RelationshipStatus.SUBSCRIBER)
        );
        verify(friendshipRepository, times(1))
                .findByTargetUserAndSourceUser(sourceUserId, targetUserId);
        verify(deletionConditionHandler, times(1))
                .handleConditionsForDeletingFriending(statusFriend, sourceUserId);
    }

    @Test
    @DisplayName("Проверка удаления из друзей два")
    void testDeleteFromFriendsTwo_Success() {
        // Arrange
        when(friendshipRepository.findByTargetUserAndSourceUser(targetUserId, sourceUserId)).thenReturn(Optional.of(statusFriend));

        FriendshipDto expectedDto = FriendshipDto.builder()
                .id(statusFriend.getId())
                .relationshipStatus(RelationshipStatus.SUBSCRIBER)
                .sourceUserId(statusFriend.getTargetUser())
                .targetUserId(statusFriend.getSourceUser())
                .isArchive(statusFriend.isArchive())
                .build();

        when(deletionConditionHandler.handleConditionsForDeletingFriending(any(), any())).thenReturn(expectedDto);

        // Act
        FriendshipDto result = friendshipService.deleteFromFriends(targetUserId , sourceUserId);

        // Assert
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result.isArchive()).isFalse(),
                () -> assertThat(result.sourceUserId()).isEqualTo(targetUserId),
                () -> assertThat(result.relationshipStatus()).isEqualTo(RelationshipStatus.SUBSCRIBER)
        );
        verify(friendshipRepository, times(1))
                .findByTargetUserAndSourceUser(sourceUserId, targetUserId);
        verify(deletionConditionHandler, times(1))
                .handleConditionsForDeletingFriending(statusFriend, sourceUserId);
    }
}