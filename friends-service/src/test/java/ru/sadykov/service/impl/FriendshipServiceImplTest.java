package ru.sadykov.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.entity.enums.RelationshipStatus;
import ru.sadykov.exception.exceptions.FriendshipNotFoundException;
import ru.sadykov.exception.exceptions.UnfriendingException;
import ru.sadykov.localization.LocalizationExceptionMessage;
import ru.sadykov.mapper.FriendshipMapper;
import ru.sadykov.repository.FriendshipRepository;
import ru.sadykov.service.addfriend.AddConditionHandler;
import ru.sadykov.service.deletefriend.ArchiveRecordingDel;
import ru.sadykov.service.deletefriend.DeletionConditionHandler;
import ru.sadykov.service.deletefriend.RequestForFriendshipDel;
import ru.sadykov.service.deletefriend.YouAreAFriendDel;
import ru.sadykov.service.deletefriend.YouAreASubscriberDel;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FriendshipServiceImplTest {

    @InjectMocks
    FriendshipServiceImpl friendshipService;

    @Mock
    FriendshipRepository friendshipRepository;

    @Mock
    AddConditionHandler addConditionHandler;

    LocalizationExceptionMessage localizationExceptionMessage;

    @BeforeEach
    public void setUp() {
        var friendshipMapper = Mappers.getMapper(FriendshipMapper.class);
        localizationExceptionMessage = new LocalizationExceptionMessage();

        var youAreAFriendDel = new YouAreAFriendDel(friendshipRepository, friendshipMapper);
        var youAreASubscriberDel = new YouAreASubscriberDel(friendshipRepository, localizationExceptionMessage, friendshipMapper);
        var requestForFriendshipDel = new RequestForFriendshipDel(friendshipRepository, friendshipMapper);
        var archiveRecordingDel = new ArchiveRecordingDel(localizationExceptionMessage);

        var conditions =
                List.of(youAreAFriendDel, youAreASubscriberDel, requestForFriendshipDel, archiveRecordingDel);

        var deletionConditionHandler =
                new DeletionConditionHandler(conditions, localizationExceptionMessage);

        friendshipService = new FriendshipServiceImpl(
                friendshipRepository,
                addConditionHandler,
                deletionConditionHandler,
                localizationExceptionMessage,
                friendshipMapper);
    }

    @DisplayName("Запись о дружбе не найдена")
    @Test
    void testDeleteFromFriendsThrowFriendshipNotFoundException() {

        var currentUserId = 1L;
        var targetUserId = 2L;
        var friendshipNotFoundExc = localizationExceptionMessage.getFriendshipNotFoundExc();

        when(friendshipRepository.findByTargetUserAndSourceUser(any(), any()))
                .thenReturn(Optional.empty());

        assertThrows(FriendshipNotFoundException.class,
                () -> friendshipService.deleteFromFriends(currentUserId, targetUserId),
                friendshipNotFoundExc
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Дружба\". currentUserId == sourceUserId")
    void testDeleteFromFriendsStatusFriendSuccessOne() {

        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusSub = RelationshipStatus.SUBSCRIBER;

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                RelationshipStatus.FRIEND, LocalDateTime.now(), false);
        var savedFriendship = new Friendship(1L, targetUserId, currentUserId,
                statusSub, LocalDateTime.now(), false);

        when(friendshipRepository.findByTargetUserAndSourceUser(currentUserId, targetUserId)).thenReturn(Optional.of(foundFriendship));
        when(friendshipRepository.save(any())).thenReturn(savedFriendship);
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(currentUserId, targetUserId);

        assertAll(
                () -> assertThat(friendshipDto).isNotNull(),
                () -> assertThat(friendshipDto.relationshipStatus()).isEqualTo(statusSub),
                () -> assertThat(friendshipDto.sourceUserId()).isEqualTo(targetUserId),
                () -> assertThat(friendshipDto.targetUserId()).isEqualTo(currentUserId),
                () -> assertThat(friendshipDto.archive()).isFalse()
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Дружба\". currentUserId == targetUserId")
    void testDeleteFromFriendsStatusFriendSuccessTwo() {

        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusSub = RelationshipStatus.SUBSCRIBER;

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                RelationshipStatus.FRIEND, LocalDateTime.now(), false);
        var savedFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusSub, LocalDateTime.now(), false);

        when(friendshipRepository.findByTargetUserAndSourceUser(targetUserId, currentUserId)).thenReturn(Optional.of(foundFriendship));
        when(friendshipRepository.save(any())).thenReturn(savedFriendship);
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(targetUserId, currentUserId);

        assertAll(
                () -> assertThat(friendshipDto).isNotNull(),
                () -> assertThat(friendshipDto.relationshipStatus()).isEqualTo(statusSub),
                () -> assertThat(friendshipDto.sourceUserId()).isEqualTo(currentUserId),
                () -> assertThat(friendshipDto.targetUserId()).isEqualTo(targetUserId),
                () -> assertThat(friendshipDto.archive()).isFalse()
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Заявка\". currentUserId == sourceUserId")
    void testDeleteFromFriendsStatusApplicationSuccessOne() {

        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusApp = RelationshipStatus.APPLICATION;

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusApp, LocalDateTime.now(), false);
        var savedFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusApp, LocalDateTime.now(), true);

        when(friendshipRepository.findByTargetUserAndSourceUser(currentUserId, targetUserId)).thenReturn(Optional.of(foundFriendship));
        when(friendshipRepository.save(any())).thenReturn(savedFriendship);
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(currentUserId, targetUserId);

        assertAll(
                () -> assertThat(friendshipDto).isNotNull(),
                () -> assertThat(friendshipDto.relationshipStatus()).isEqualTo(statusApp),
                () -> assertThat(friendshipDto.sourceUserId()).isEqualTo(currentUserId),
                () -> assertThat(friendshipDto.targetUserId()).isEqualTo(targetUserId),
                () -> assertThat(friendshipDto.archive()).isTrue()
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Заявка\". currentUserId == targetUserId")
    void testDeleteFromFriendsStatusApplicationSuccessTwo() {

        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusSub = RelationshipStatus.SUBSCRIBER;

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                RelationshipStatus.APPLICATION, LocalDateTime.now(), false);
        var savedFriendship = new Friendship(1L, targetUserId, currentUserId,
                statusSub, LocalDateTime.now(), false);

        when(friendshipRepository.findByTargetUserAndSourceUser(targetUserId, currentUserId)).thenReturn(Optional.of(foundFriendship));
        when(friendshipRepository.save(any())).thenReturn(savedFriendship);
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(targetUserId, currentUserId);

        assertAll(
                () -> assertThat(friendshipDto).isNotNull(),
                () -> assertThat(friendshipDto.relationshipStatus()).isEqualTo(statusSub),
                () -> assertThat(friendshipDto.sourceUserId()).isEqualTo(targetUserId),
                () -> assertThat(friendshipDto.targetUserId()).isEqualTo(currentUserId),
                () -> assertThat(friendshipDto.archive()).isFalse()
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Подписчик\". currentUserId == sourceUserId")
    void testDeleteFromFriendsStatusSubscriberSuccessOne() {
        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusSub = RelationshipStatus.SUBSCRIBER;

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusSub, LocalDateTime.now(), false);
        var savedFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusSub, LocalDateTime.now(), true);

        when(friendshipRepository.findByTargetUserAndSourceUser(currentUserId, targetUserId)).thenReturn(Optional.of(foundFriendship));
        when(friendshipRepository.save(any())).thenReturn(savedFriendship);
        FriendshipDto friendshipDto = friendshipService.deleteFromFriends(currentUserId, targetUserId);

        assertAll(
                () -> assertThat(friendshipDto).isNotNull(),
                () -> assertThat(friendshipDto.relationshipStatus()).isEqualTo(statusSub),
                () -> assertThat(friendshipDto.sourceUserId()).isEqualTo(currentUserId),
                () -> assertThat(friendshipDto.targetUserId()).isEqualTo(targetUserId),
                () -> assertThat(friendshipDto.archive()).isTrue()
        );
    }

    @Test
    @DisplayName("Удаление из друзей. Статус \"Подписчик\". currentUserId == targetUserId")
    void testDeleteFromFriendsStatusSubscriberSuccessTwo() {
        var currentUserId = 1L;
        var targetUserId = 2L;
        var statusSub = RelationshipStatus.SUBSCRIBER;
        var deleteUserAreSubExc = localizationExceptionMessage.getDeleteUserAreSubExc();

        var foundFriendship = new Friendship(1L, currentUserId, targetUserId,
                statusSub, LocalDateTime.now(), false);

        when(friendshipRepository.findByTargetUserAndSourceUser(targetUserId, currentUserId)).thenReturn(Optional.of(foundFriendship));


        assertThrows(UnfriendingException.class,
                () -> friendshipService.deleteFromFriends(targetUserId, currentUserId),
                deleteUserAreSubExc
        );
    }
}