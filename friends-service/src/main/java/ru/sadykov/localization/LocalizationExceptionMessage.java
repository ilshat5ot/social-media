package ru.sadykov.localization;

import lombok.Getter;

import java.util.Locale;

@Getter
public class LocalizationExceptionMessage {

    private final MessageSource ms = new MessageSource();

    private final Locale locale;

    private final String addFromFriendsExc;
    private final String isAFriendExc;
    private final String applicationHasAlreadyBeenSentExc;
    private final String areYouASubExc;
    private final String leftAsASubExc;
    private final String invalidRequest;

    public LocalizationExceptionMessage(Locale locale) {
        this.locale = locale;

        addFromFriendsExc = ms.getMessage("add.from.friends", null, locale);
        isAFriendExc = ms.getMessage("is.a.friend", null, locale);
        applicationHasAlreadyBeenSentExc = ms.getMessage("application.has.already.been.sent", null, locale);
        areYouASubExc = ms.getMessage("are.you.a.sub", null, locale);
        leftAsASubExc = ms.getMessage("left.as.a.sub", null, locale);
        invalidRequest = ms.getMessage("invalid.request", null, locale);
    }
}
