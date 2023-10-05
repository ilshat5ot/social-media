package ru.sadykov.localization;

import lombok.Getter;

import java.util.Locale;

@Getter
public class LocalizationExceptionMessage {

    private final ExceptionMessageSource ms = new ExceptionMessageSource();

    private final Locale locale;

    private final String addFromFriendsExc;
    private final String isAFriendExc;
    private final String applicationHasAlreadyBeenSentExc;
    private final String areYouASubExc;
    private final String leftAsASubExc;
    private final String invalidRequestExc;
    private final String deleteHimselfExc;
    private final String deleteUserAreSubExc;

    public LocalizationExceptionMessage(Locale locale) {
        this.locale = locale;

        addFromFriendsExc = ms.getMessage("add.from.friends", null, locale);
        isAFriendExc = ms.getMessage("is.a.friend", null, locale);
        applicationHasAlreadyBeenSentExc = ms.getMessage("application.has.already.been.sent", null, locale);
        areYouASubExc = ms.getMessage("are.you.a.sub", null, locale);
        leftAsASubExc = ms.getMessage("left.as.a.sub", null, locale);
        invalidRequestExc = ms.getMessage("invalid.request", null, locale);
        deleteHimselfExc = ms.getMessage("friendship.delete.yourself", null, locale);
        deleteUserAreSubExc = ms.getMessage("friendship.delete.subscriber", null, locale);
    }
}
