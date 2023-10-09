package ru.sadykov.service.deletefriend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.sadykov.dto.FriendshipDto;
import ru.sadykov.entity.Friendship;
import ru.sadykov.exception.exceptions.UnfriendingException;
import ru.sadykov.localization.LocalizationExceptionMessage;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ArchiveRecording implements ConditionsForDeletingFriend {

    private final LocalizationExceptionMessage localizationExceptionMessage;

    @Override
    public Optional<FriendshipDto> processTheTermsOfDeletingFromFriends(Friendship friendship, Long currentUser) {
        if (friendship.isArchive()) {
            throw new UnfriendingException(localizationExceptionMessage.getDeleteFriendExc());
        }
        return Optional.empty();
    }
}
